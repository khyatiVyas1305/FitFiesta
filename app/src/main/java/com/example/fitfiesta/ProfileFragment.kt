package com.example.fitfiesta

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var switch: SwitchCompat
    lateinit var notificationSwitch: SwitchCompat
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedViewModel: SharedViewModel
    lateinit var buttonNext: Button
    var userSteps: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        switch = view.findViewById(R.id.darkSwitch)
        sharedPreferences = requireContext().getSharedPreferences("Mode", Context.MODE_PRIVATE)

        // Initialize the sharedViewModel
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        notificationSwitch = view.findViewById(R.id.notificationSwitch)
        val notificationManager =
            requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

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
                scheduleDailyNotification()
            } else {
                // User disabled notifications
                cancelNotification(1)
                cancelNotification(2)
            }

            // Save the state in preferences
            saveNotificationStateToPreferences(isChecked)
        }

        val isNightMode = sharedPreferences.getBoolean("night_mode", false)
        switch.isChecked = isNightMode

        switch.setOnCheckedChangeListener { _, isChecked ->
            val mode = if (isChecked) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(mode)
            saveNightModeState(isChecked)
        }

        return view
    }

    private fun saveNightModeState(isNightMode: Boolean) {
        sharedPreferences.edit().putBoolean("night_mode", isNightMode).apply()
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
        val message = "You completed $userSteps steps."
        intent.putExtra(titleExtra,title)
        intent.putExtra(messageExtra,message)
//        intent.putExtra(NOTIFICATION_ACTION_EXTRA, OPEN_HOME_FRAGMENT)

        val notificationId = 2 // Unique identifier

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Set up repeating alarm
        val intervalMillis = 1 * 60 * 1000 // minutes
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

        Toast.makeText(requireContext(),"You have enabled Notifications!", Toast.LENGTH_SHORT).show()
    }

    private fun scheduleDailyNotification() {
        val intent = Intent(requireContext(), Notification::class.java)
        val title = "Good Evening"
        val message = "Rise and shine! Did you check today's Nutrition Fact?"
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)
//        intent.putExtra(NOTIFICATION_ACTION_EXTRA, OPEN_HOME_FRAGMENT)

        val notificationId = 1 // Unique identifier for the daily notification

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Set up the alarm to trigger every day at 7 AM
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 3)
        calendar.set(Calendar.SECOND, 0)

        val triggerMillis = calendar.timeInMillis
        if (triggerMillis <= System.currentTimeMillis()) {
            // If the trigger time has already passed today, set it for tomorrow
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }
    }


    private fun saveNotificationStateToPreferences(enabled: Boolean) {
        // Save the state to SharedPreferences
        val sharedPreferences =
            requireContext().getSharedPreferences("NotificationPreferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("notification_enabled", enabled).apply()
    }

    private fun cancelNotification(notificationId: Int) {
        val intent = Intent(requireContext(), Notification::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)

        // Additionally, you may want to cancel any existing notifications in the NotificationManager
        val notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(notificationId)
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}