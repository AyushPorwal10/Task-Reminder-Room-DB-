package com.example.checkingagp.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.checkingagp.R


const val notificationId = 111
const val channelId ="task_notification"
const val TASK_TITLE = "You have pending Task"

const val TASK_CONTENT = "taskContent"

class SendNotification : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val taskContent = intent?.getStringExtra(TASK_CONTENT)

        Log.d("TaskNotification","On receive executed with taskContent $taskContent <-")

        val notification = NotificationCompat.Builder(context , channelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(TASK_TITLE) // this is just to show that you have pending task so this is hardcoded
            .setContentText(taskContent)
            .build()


        val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.notify(notificationId , notification)

    }
}