package com.kyc.mobile.di

import android.content.Context
import com.kyc.mobile.BuildConfig

interface AppModule{
    val networkModule: NetworkModule
    val featureModule: FeatureModule
    val dataStoreModule: DataStoreModule
}

class AppModuleImpl(
    private val context: Context
): AppModule {

    override val networkModule: NetworkModule by lazy{
        if(BuildConfig.MOCK_API){
            MockNetworkModuleImpl(context)
        }
        else{
            NetworkModuleImpl(dataStoreModule)
        }
    }

    override val featureModule: FeatureModule by lazy{
        FeatureModuleImpl(dataStoreModule,networkModule)
    }

    override val dataStoreModule: DataStoreModule by lazy{
        DataStoreModuleImpl(context)
    }
}