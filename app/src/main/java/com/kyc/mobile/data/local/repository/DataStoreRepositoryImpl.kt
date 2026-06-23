package com.kyc.mobile.data.local.repository

import androidx.datastore.core.DataStore
import com.kyc.mobile.data.util.SingletonHolder
import com.kyc.mobile.domain.model.UserPreferences
import com.kyc.mobile.domain.usecase.DataStoreRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull


class DataStoreRepositoryImpl private constructor(
    val dataStore: DataStore<UserPreferences>
): DataStoreRepository {

    override suspend fun saveToDataStore(data: UserPreferences) {

        val record = dataStore.data.firstOrNull()
        var token = "";
        if(record!=null){
            token = record.token
        }

        dataStore.updateData {
            data.copy(token = data.token.takeIf { it.isNotBlank() } ?: token)
        }
    }

    override suspend fun getUserPreferencesFromDataStore(): UserPreferences {
        return dataStore.data.firstOrNull() ?: UserPreferences()
    }

    companion object: SingletonHolder<DataStoreRepositoryImpl,DataStore<UserPreferences>>(::DataStoreRepositoryImpl)
}