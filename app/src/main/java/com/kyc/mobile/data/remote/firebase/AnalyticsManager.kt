package com.kyc.mobile.data.remote.firebase

import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kyc.mobile.domain.exception.KycMobileException
import com.kyc.mobile.domain.usecase.AnalyticsRepository
import kotlin.collections.component1
import kotlin.collections.component2

class AnalyticsManager(
    private val analytics: FirebaseAnalytics,
    private val crashlytics: FirebaseCrashlytics
): AnalyticsRepository {

    @Override
    override fun logScreenView(screenName: String){

        analytics.logEvent(FirebaseAnalytics.Param.SCREEN_NAME, Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenName)
        })
    }

    @Override
    override fun logEvent(event: String, data: Map<String, String>){

        analytics.logEvent(event, Bundle().apply {
            data.forEach { (key, value) ->
                putString(key,value)
                Log.i("AnalyticsManager", "$key - $value")
            }
        })
    }

    @Override
    override fun logException(screenName: String, ex: Exception){

        if (ex is KycMobileException){

            ex.errorData?.let {
                crashlytics.setCustomKey("code",it.code)
                crashlytics.setCustomKey("time", it.time.toString())
            }
        }
        crashlytics.setCustomKey("screen",screenName)
        crashlytics.recordException(ex)
    }
}