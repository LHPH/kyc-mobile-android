package com.kyc.mobile.di

import com.kyc.mobile.data.remote.CustomerApplicationRepositoryImpl
import com.kyc.mobile.data.remote.CustomerNotificationRepositoryImpl
import com.kyc.mobile.data.remote.CustomerTrackActionRepositoryImpl
import com.kyc.mobile.data.remote.LoginRepositoryImpl
import com.kyc.mobile.domain.usecase.CustomerApplicationRepository
import com.kyc.mobile.domain.usecase.CustomerNotificationRepository
import com.kyc.mobile.domain.usecase.CustomerTrackActionRepository
import com.kyc.mobile.domain.usecase.LoginRepository

interface FeatureModule{
    val loginRepository: LoginRepository
    val customerApplicationRepository: CustomerApplicationRepository
    val customerTrackActionRepository: CustomerTrackActionRepository

    val customerNotificationRepository: CustomerNotificationRepository
}

class FeatureModuleImpl(
    val dataStoreModule: DataStoreModule,
    val networkModule: NetworkModule
): FeatureModule{

    override val loginRepository: LoginRepository by lazy{
        LoginRepositoryImpl(
            networkModule.authApi,
            dataStoreModule.dataStoreRepository)
    }

    override val customerApplicationRepository: CustomerApplicationRepository by lazy{
        CustomerApplicationRepositoryImpl(networkModule.customerApplicationApi)
    }

    override val customerTrackActionRepository: CustomerTrackActionRepository by lazy{
        CustomerTrackActionRepositoryImpl(networkModule.customerTrackActionApi)
    }

    override val customerNotificationRepository: CustomerNotificationRepository by lazy{
        CustomerNotificationRepositoryImpl(networkModule.notificationApi)
    }
}