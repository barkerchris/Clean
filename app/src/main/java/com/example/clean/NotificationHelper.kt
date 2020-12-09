package com.example.clean

import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import android.app.Notification
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color

//This makes the notification channel
internal class NotificationHelper
    (base: Context) : ContextWrapper(base) {
    private var notifManager: NotificationManager? = null

    //Send your notifications to the NotificationManager system service
    private val manager: NotificationManager?
        get() {
            if (notifManager == null) {
                notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            return notifManager
        }

    init {
        createChannels()
    }

    private fun createChannels() {
        val notificationChannel =
            NotificationChannel(
                CHANNEL_ONE_ID,
                CHANNEL_ONE_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.setShowBadge(true)
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        manager!!.createNotificationChannel(notificationChannel)
    }

    //Create the notification that’ll be posted to Channel One
    fun getNotification1(title: String, body: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(applicationContext, CHANNEL_ONE_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_notif_article)
            .setAutoCancel(true)
            .setContentIntent(PendingIntent.getActivity(this, 0,
                Intent(this, MainActivity::class.java), 0))
    }

    fun notify(id: Int, notification: NotificationCompat.Builder) {
        manager!!.notify(id, notification.build())
    }

    companion object {
        const val CHANNEL_ONE_ID = "com.example.clean.ONE"
        const val CHANNEL_ONE_NAME = "GENERAL"
    }

}