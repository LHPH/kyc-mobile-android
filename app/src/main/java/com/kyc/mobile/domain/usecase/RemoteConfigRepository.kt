package com.kyc.mobile.domain.usecase

import kotlinx.coroutines.flow.Flow

interface RemoteConfigRepository {

    fun fetchAndActivate(onComplete: (Boolean) -> Unit)
    fun getConfigStringValue(key: String): String

    fun fetchUpdate(key: String): Flow<String>
}