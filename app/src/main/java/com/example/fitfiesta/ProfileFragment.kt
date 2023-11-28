package com.example.fitfiesta

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


class ProfileFragment : Fragment() {

    //lateinit var notificationBtn: Button
    lateinit var notificationSwitch: Switch
    private lateinit var sharedViewModel: SharedViewModel
    var userSteps: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        // Initialize the sharedViewModel
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val notificationManager =
            requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }


        //notificationBtn = view.findViewById(R.id.notificationBtn)
        notificationSwitch = view.findViewById(R.id.notificationSwitch)
//        notificationBtn.setOnClickListener {
//            scheduleNotification()
//        }

        // Observe changes to totalSteps and update UI
        sharedViewModel.totalSteps.observe(viewLifecycleOwner, Observer { steps ->
            // Check if the fragment's view is available before updating UI
            if (view != null) {
                userSteps = steps
            }
        })

        notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // User enabled notifications
                scheduleNotification()
            } else {
                // User disabled notifications
                cancelNotification()
            }

            // Save the state in preferences
            saveNotificationStateToPreferences(isChecked)
        }
        return view
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Notification channel"
        val desc = "Notification description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
            notificationManager.createNotificationChannel(channel)
        }

    }

    private fun scheduleNotification() {
        val intent = Intent(requireContext(), Notification::class.java)
        val title = "You're doing great!"
        val message = "You completed $userSteps."
        intent.putExtra(titleExtra,title)
        intent.putExtra(messageExtra,message)

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Set up repeating alarm every 30 minutes
        val intervalMillis = 2 * 60 * 1000 // 30 minutes
        val triggerMillis = SystemClock.elapsedRealtime() + intervalMillis

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerMillis,
                pendingIntent
            )
        } else {
            alarmManager.setRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerMillis,
                intervalMillis.toLong(),
                pendingIntent
            )
        }

        Toast.makeText(requireContext(),"You have enabled Notifications!",Toast.LENGTH_SHORT).show()
    }

    private fun saveNotificationStateToPreferences(enabled: Boolean) {
        // Save the state to SharedPreferences
        val sharedPreferences =
            requireContext().getSharedPreferences("NotificationPreferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("notification_enabled", enabled).apply()
    }

    private fun cancelNotification() {
        val intent = Intent(requireContext(), Notification::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)

        // Additionally, you may want to cancel any existing notifications in the NotificationManager
        val notificationManager =
            requireContext().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(notificationID)
    }


}