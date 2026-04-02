package com.kyc.mobile.domain.usecase

interface AnalyticsRepository {

    fun logScreenView(screenName: String)
    fun logEvent(event: String, data: Map<String, String>)
    fun logException(screenName: String = "Unknown", ex: Exception)
}