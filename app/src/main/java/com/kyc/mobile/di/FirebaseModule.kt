package com.kyc.mobile.di

import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.crashlytics.crashlytics
import com.kyc.mobile.data.remote.firebase.AnalyticsManager
import com.kyc.mobile.domain.usecase.AnalyticsRepository

interface FirebaseModule{
    val analyticsManager: AnalyticsRepository
}

class FirebaseModuleImpl: FirebaseModule{

    override val analyticsManager: AnalyticsRepository by lazy {
        AnalyticsManager(analytics = Firebase.analytics, crashlytics = Firebase.crashlytics)
    }
}

