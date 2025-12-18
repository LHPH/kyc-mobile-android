package com.kyc.mobile.di

import com.kyc.mobile.data.remote.CustomerApplicationRepositoryImpl
import com.kyc.mobile.data.remote.LoginRepositoryImpl
import com.kyc.mobile.domain.usecase.CustomerApplicationRepository
import com.kyc.mobile.domain.usecase.LoginRepository

interface FeatureModule{
    val loginRepository: LoginRepository
    val customerApplicationRepository: CustomerApplicationRepository
}

class FeatureModuleImpl(
    val dataStoreModule: DataStoreModule,
    val networkModule: NetworkModule
): FeatureModule{

    override val loginRepository: LoginRepository by lazy{
        LoginRepositoryImpl(networkModule.authApi,dataStoreModule.dataStoreRepository)
    }

    override val customerApplicationRepository: CustomerApplicationRepository by lazy{
        CustomerApplicationRepositoryImpl(networkModule.customerApplicationApi)
    }
}