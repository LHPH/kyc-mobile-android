package com.kyc.mobile.domain.usecase

interface RemoteConfigRepository {

    fun fetchAndActivate(onComplete: (Boolean) -> Unit)
    fun getConfigStringValue(key: String): String
}