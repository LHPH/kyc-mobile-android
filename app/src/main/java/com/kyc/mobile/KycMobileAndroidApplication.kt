package com.kyc.mobile

import android.app.Application
import com.kyc.mobile.di.AppModule
import com.kyc.mobile.di.AppModuleImpl

class KycMobileAndroidApplication(): Application(){

    companion object{
        lateinit var appModule: AppModule
    }

    override fun onCreate(){
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}