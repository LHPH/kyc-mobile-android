package com.kyc.mobile.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.kyc.mobile.data.local.repository.DataStoreRepositoryImpl
import com.kyc.mobile.data.local.LocalDatabase
import com.kyc.mobile.data.local.repository.PropertiesRepositoryImpl
import com.kyc.mobile.domain.model.UserPreferences
import com.kyc.mobile.domain.model.UserPreferencesSerializable
import com.kyc.mobile.domain.usecase.DataStoreRepository
import com.kyc.mobile.domain.usecase.PropertiesRepository

/*val Context.dataStore by dataStore(
    fileName = "user-preferences",
    serializer = UserPreferencesSerializable
)*/

interface DataStoreModule {
    val dataStoreRepository: DataStoreRepository
    val dataBase: LocalDatabase
    val propertiesRepository: PropertiesRepository
}

class DataStoreModuleImpl(
    context: Context,
    securityModule: SecurityModule
): DataStoreModule{

    private val dataStore: DataStore<UserPreferences> by lazy{

        DataStoreFactory.create(
            serializer = UserPreferencesSerializable(securityModule.aead),
            produceFile = {
                context.dataStoreFile("user-preferences")
            }
        )
    }

    override val dataStoreRepository: DataStoreRepository by lazy{
        DataStoreRepositoryImpl.getInstance(dataStore)
    }

    override val dataBase: LocalDatabase by lazy{
        LocalDatabase.getInstance(context)
    }

    override val propertiesRepository by lazy{
        PropertiesRepositoryImpl(dataBase.propertyDao())
    }
}