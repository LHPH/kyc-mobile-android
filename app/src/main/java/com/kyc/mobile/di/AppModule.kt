package com.kyc.mobile.di

import android.content.Context
import com.kyc.mobile.BuildConfig

interface AppModule{
    val networkModule: NetworkModule
    val featureModule: FeatureModule
    val dataStoreModule: DataStoreModule
    val firebaseModule: FirebaseModule
    val googleServicesModule: GoogleServicesModule
    val securityModule: SecurityModule
}

class AppModuleImpl(
    private val context: Context
): AppModule {

    override val networkModule: NetworkModule by lazy{
        if(BuildConfig.MOCK_API){
            MockNetworkModuleImpl(context)
        }
        else{
            NetworkModuleImpl(dataStoreModule,firebaseModule,securityModule)
        }
    }

    override val featureModule: FeatureModule by lazy{
        FeatureModuleImpl(dataStoreModule,networkModule)
    }

    override val dataStoreModule: DataStoreModule by lazy{
        DataStoreModuleImpl(context)
    }

   override val firebaseModule: FirebaseModule by lazy {
        FirebaseModuleImpl()
    }

    override val googleServicesModule: GoogleServicesModule by lazy{
        GoogleServicesModuleImpl(context)
    }

    override val securityModule: SecurityModule by lazy{
        SecurityModuleImpl()
    }
}