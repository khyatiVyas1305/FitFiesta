package com.example.fitfiesta

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat


const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"

class Notification: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationId = System.currentTimeMillis().toInt()

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
//
//    private fun displayNotification(context: Context, intent: Intent, notificationId: Int) {
//        val notification = NotificationCompat.Builder(context, channelID)
//            .setSmallIcon(R.drawable.ic_steps)
//            .setContentTitle(intent.getStringExtra(titleExtra))
//            .setContentText(intent.getStringExtra(messageExtra))
//            .build()
//
//        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        manager.notify(notificationId, notification)
//    }
}