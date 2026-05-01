package com.kyc.mobile.data.remote.firebase

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kyc.mobile.MainActivity
import com.kyc.mobile.R
import kotlin.random.Random


class MessagingService: FirebaseMessagingService(){

    override fun onMessageReceived(message: RemoteMessage) {

        message.notification?.let { notification ->
            sendNotification(notification.title?:"New Notification",notification.body?:"")
        }
    }

    fun sendNotification(title: String, body: String){

        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(this,0,intent,FLAG_IMMUTABLE)

        val channelId = this.getString(R.string.firebase_notification_channel_id)
        val notificationBuilder = NotificationCompat.Builder(this,channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.mail_24px)

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(Random.nextInt(),notificationBuilder.build())

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}