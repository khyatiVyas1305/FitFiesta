package com.example.fitfiesta

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

//const val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"
const val NOTIFICATION_ACTION_EXTRA = "notificationAction"

class Notification: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

//        if (context != null && intent != null) {
//            val notificationAction = intent.getStringExtra(NOTIFICATION_ACTION_EXTRA)
//
//            when (notificationAction) {
//                OPEN_HOME_FRAGMENT -> {
//                    // Handle opening HomeFragment
//                    openHomeFragment(context)
//                }
//                // Add other cases for different notification actions if needed
//                else -> {
//                    // Handle other notification actions or default behavior
//                    // For example, display the notification
                    val notificationId = System.currentTimeMillis().toInt()
//                    displayNotification(context, intent, notificationId)
//                }
//            }
//        }
        val notification = context?.let {
            NotificationCompat.Builder(it, channelID)
                .setSmallIcon(R.drawable.ic_steps)
                .setContentTitle(intent?.getStringExtra(titleExtra))
                .setContentText(intent?.getStringExtra(messageExtra))
                .build()
        }

        val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationId,notification)
    }

//    private fun openHomeFragment(context: Context) {
//        // Handle the logic to navigate to the HomeFragment
//        // Replace "YourActivity" with the name of your hosting activity
//        val intent = Intent(context, HomeFragment::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        intent.putExtra(NOTIFICATION_ACTION_EXTRA, OPEN_HOME_FRAGMENT)
//        context.startActivity(intent)
//    }

    private fun displayNotification(context: Context, intent: Intent, notificationId: Int) {
        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_steps)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationId, notification)
    }

//    companion object {
//        const val OPEN_HOME_FRAGMENT = "openHomeFragment"
//        // Add other notification action constants if needed
//    }
}