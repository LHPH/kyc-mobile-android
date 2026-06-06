package com.kyc.mobile.di

import android.content.Context
import com.kyc.mobile.data.local.DataStoreRepositoryImpl
import com.kyc.mobile.data.local.LocalDatabase
import com.kyc.mobile.data.local.PropertiesRepositoryImpl
import com.kyc.mobile.dataStore
import com.kyc.mobile.domain.usecase.DataStoreRepository
import com.kyc.mobile.domain.usecase.PropertiesRepository

interface DataStoreModule {
    val dataStoreRepository: DataStoreRepository
    val dataBase: LocalDatabase
    val propertiesRepository: PropertiesRepository
}

class DataStoreModuleImpl(
    context: Context
): DataStoreModule{

    override val dataStoreRepository: DataStoreRepository by lazy{
        DataStoreRepositoryImpl.getInstance(context.dataStore)
    }

    override val dataBase: LocalDatabase by lazy{
        LocalDatabase.getInstance(context)
    }

    override val propertiesRepository by lazy{
        PropertiesRepositoryImpl(dataBase.propertyDao())
    }
}