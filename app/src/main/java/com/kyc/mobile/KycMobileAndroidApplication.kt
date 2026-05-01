package com.kyc.mobile

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.datastore.dataStore
import com.kyc.mobile.di.AppModule
import com.kyc.mobile.di.AppModuleImpl
import com.kyc.mobile.domain.model.UserPreferencesSerializable

val Context.dataStore by dataStore(
    fileName = "user-preferences",
    serializer = UserPreferencesSerializable
)

class KycMobileAndroidApplication(): Application(){

    companion object{
        lateinit var appModule: AppModule
    }

    override fun onCreate(){
        super.onCreate()
        appModule = AppModuleImpl(this)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            val channelId = this.getString(R.string.firebase_notification_channel_id)
            val channel = NotificationChannel(channelId, "FCM_NOTIFICATION_CHANNEL", NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }

        /*FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if(it.isSuccessful){
                println(it.result)
            }
        }*/
    }
}