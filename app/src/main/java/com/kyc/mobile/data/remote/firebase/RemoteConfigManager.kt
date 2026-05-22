package com.kyc.mobile.data.remote.firebase

import android.util.Log
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.kyc.mobile.BuildConfig
import com.kyc.mobile.R
import com.kyc.mobile.domain.usecase.RemoteConfigRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class RemoteConfigManager(
    private val remoteConfig: FirebaseRemoteConfig
): RemoteConfigRepository {

    init{
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = BuildConfig.REMOTE_CONFIG_MINIMUM_FETCH_INTERVAL_SEC
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        val latch = CountDownLatch(1)

        remoteConfig.fetchAndActivate().addOnCompleteListener {
            latch.countDown()
        }

        try {
            latch.await(2, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun fetchAndActivate(onComplete: (Boolean) -> Unit){

        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            Log.i("RemoteConfigManager","Successful: ${task.isSuccessful}")
            Log.i("RemoteConfigManager","Updated param: ${task.result}")
            onComplete(task.isSuccessful)
        }
    }

    override fun fetchUpdate(key: String): Flow<String> = callbackFlow {

        //Initial fetch and activate
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            trySend(getConfigStringValue(key))
        }

        //Real Time listener
        val registration = remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener{
            override fun onUpdate(configUpdate: ConfigUpdate) {

                if(configUpdate.updatedKeys.contains(key)){
                    remoteConfig.activate().addOnCompleteListener {
                        trySend(getConfigStringValue(key))
                    }
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {
                close(error)
            }
        })

        //Clean up listener when the flow is closed
        awaitClose { registration.remove() }

    }

    override fun getConfigStringValue(key: String): String{
        val value = remoteConfig.getString(key)
        Log.i("RemoteConfigManager",value)
        return value
    }

}