package com.kyc.mobile.di

import android.content.Context
import com.kyc.mobile.data.local.DataStoreRepositoryImpl
import com.kyc.mobile.dataStore
import com.kyc.mobile.domain.usecase.DataStoreRepository

interface DataStoreModule {
    val dataStoreRepository: DataStoreRepository
}

class DataStoreModuleImpl(
    context: Context
): DataStoreModule{

    override val dataStoreRepository: DataStoreRepository by lazy{
        DataStoreRepositoryImpl.getInstance(context.dataStore)
    }
}