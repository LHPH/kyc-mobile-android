package com.kyc.mobile.di

import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.crashlytics.crashlytics
import com.google.firebase.remoteconfig.remoteConfig
import com.kyc.mobile.data.remote.firebase.AnalyticsManager
import com.kyc.mobile.data.remote.firebase.RemoteConfigManager
import com.kyc.mobile.domain.usecase.AnalyticsRepository
import com.kyc.mobile.domain.usecase.RemoteConfigRepository

interface FirebaseModule{
    val analyticsManager: AnalyticsRepository
    val remoteConfigManager: RemoteConfigRepository
}

class FirebaseModuleImpl: FirebaseModule{

    override val analyticsManager: AnalyticsRepository by lazy {
        AnalyticsManager(analytics = Firebase.analytics, crashlytics = Firebase.crashlytics)
    }

    override val remoteConfigManager: RemoteConfigRepository by lazy{
        RemoteConfigManager(remoteConfig = Firebase.remoteConfig)
    }
}

