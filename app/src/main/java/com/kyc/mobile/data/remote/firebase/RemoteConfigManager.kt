package com.kyc.mobile.data.remote.firebase

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.kyc.mobile.R
import com.kyc.mobile.domain.usecase.RemoteConfigRepository

class RemoteConfigManager(
    private val remoteConfig: FirebaseRemoteConfig
): RemoteConfigRepository {

    init{
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 30
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
    }

    override fun fetchAndActivate(onComplete: (Boolean) -> Unit){

        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            Log.i("RemoteConfigManager","Successful: ${task.isSuccessful}")
            Log.i("RemoteConfigManager","Updated param: ${task.result}")
            onComplete(task.isSuccessful)
        }
    }

    override fun getConfigStringValue(key: String): String{
        val value = remoteConfig.getString(key)
        Log.i("RemoteConfigManager",value)
        return value
    }

}