package com.example.todoapp.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.todoapp.R
import com.example.todoapp.ui.home.activity.TaskActivity

class NotificationHelper(val context: Context) {
    private val channelId = "todo_app_channel"
    private val channelName = "Task Chanel"
    private val importance = NotificationManager.IMPORTANCE_HIGH
    private val notificationId = 1

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName,importance ).apply {
                description = "Reminder Channel Description"
            }
            val notificationManager =  context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun createNotification(title: String, message: String){
        createNotificationChannel()
        val intent = Intent(context, TaskActivity:: class.java).apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) PendingIntent.FLAG_IMMUTABLE else 0)
        val icon = BitmapFactory.decodeResource(context.resources, R.drawable.logo)
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.edit)
            .setLargeIcon(icon)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(icon).bigLargeIcon(null))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        NotificationManagerCompat.from(context).notify(notificationId, notification)

    }
}