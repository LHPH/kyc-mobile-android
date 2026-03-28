package com.kyc.mobile.di

import com.kyc.mobile.data.remote.repository.CustomerApplicationRepositoryImpl
import com.kyc.mobile.data.remote.repository.CustomerBillRepositoryImpl
import com.kyc.mobile.data.remote.repository.CustomerNotificationRepositoryImpl
import com.kyc.mobile.data.remote.repository.CustomerTrackActionRepositoryImpl
import com.kyc.mobile.data.remote.repository.LoginRepositoryImpl
import com.kyc.mobile.domain.usecase.CustomerApplicationRepository
import com.kyc.mobile.domain.usecase.CustomerBillRepository
import com.kyc.mobile.domain.usecase.CustomerNotificationRepository
import com.kyc.mobile.domain.usecase.CustomerTrackActionRepository
import com.kyc.mobile.domain.usecase.LoginRepository

interface FeatureModule{
    val loginRepository: LoginRepository
    val customerApplicationRepository: CustomerApplicationRepository
    val customerTrackActionRepository: CustomerTrackActionRepository

    val customerNotificationRepository: CustomerNotificationRepository
    val customerBillsRepository: CustomerBillRepository
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

    override val customerBillsRepository: CustomerBillRepository by lazy{
        CustomerBillRepositoryImpl(networkModule.customerBillsApi)
    }

}