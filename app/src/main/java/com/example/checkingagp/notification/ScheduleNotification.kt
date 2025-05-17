package com.example.checkingagp.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.util.Calendar


object  ScheduleNotification {


    @RequiresApi(Build.VERSION_CODES.O)
     fun createNotificationChannel(context : Context) {
        val name = "Notify Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("task_notification", name, importance)
        channel.description = desc

        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }


     @SuppressLint("ScheduleExactAlarm")
     fun scheduleNotification(context : Context, title : String, time : Long , taskId : Long){
        val notificationIntent = Intent(context ,SendNotification::class.java)
         Log.d("TaskNotification","Going to schedule task notification for title $title and time $time <-")
        notificationIntent.putExtra("taskContent",title)

        val pendingIntent = PendingIntent.getBroadcast(context , taskId.toInt() , notificationIntent , PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

          val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager


         alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP , time , pendingIntent)
    }


     fun getTimeInMillis(selectedHour: Int, selectedMinute: Int): Long {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, selectedHour)
            set(Calendar.MINUTE, selectedMinute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            // if time passed than  schedule for tomorrow
            if (timeInMillis < System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }
        return calendar.timeInMillis
    }


}