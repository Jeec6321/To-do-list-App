package com.android.todolist.Managers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.android.todolist.R

class ManagerNotification {

    object NotificationConstants {
        val CHANNEL_ID = "tasks_chanel"
        val NOTIFICATION_ID = 632
    }

    companion object{
        fun createNotificationChanel(context: Context){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val name = "Notification title"
                val descriptionText = "Description text"
                val importance = NotificationManager.IMPORTANCE_DEFAULT

                val channel = NotificationChannel(NotificationConstants.CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }

                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }

        fun sendNotification(context: Context){
            val builder = NotificationCompat.Builder(context, NotificationConstants.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_delete_24)
                .setContentTitle("¡¡Bienvenido!!")
                //.setContentText("Bienvenido")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(context)){
                notify(NotificationConstants.NOTIFICATION_ID, builder.build())
            }

        }
    }

}