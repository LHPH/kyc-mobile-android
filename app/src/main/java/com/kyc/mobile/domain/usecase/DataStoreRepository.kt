package com.kyc.mobile.domain.usecase

import com.kyc.mobile.domain.model.UserPreferences

interface DataStoreRepository {

    suspend fun saveToDataStore(data: UserPreferences)

    suspend fun getUserPreferencesFromDataStore(): UserPreferences
}