package com.kyc.mobile.di

import android.content.Context
import com.kyc.mobile.data.local.LocationRepositoryImpl
import com.kyc.mobile.domain.usecase.LocationRepository


interface GoogleServicesModule {
    val location: LocationRepository
}

class GoogleServicesModuleImpl(
    val context: Context
): GoogleServicesModule{

    override val location: LocationRepository by lazy {
        LocationRepositoryImpl(context)
    }

}