package com.kyc.mobile

import android.app.Application
import android.content.Context
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
    }
}