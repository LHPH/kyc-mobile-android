package com.kyc.mobile.data.local.repository

import android.util.Log
import com.kyc.mobile.domain.model.LocalProperty
import com.kyc.mobile.domain.model.PublicKeyData
import com.kyc.mobile.domain.usecase.DataStoreRepository
import com.kyc.mobile.domain.usecase.PropertiesRepository
import com.kyc.mobile.domain.usecase.PublicKeyRepository
import com.kyc.mobile.domain.usecase.PublicRepository
import com.kyc.mobile.domain.util.PropertyKeyEnum
import com.kyc.mobile.ui.viewmodel.INTRO_SCREEN_TAG
import java.util.concurrent.TimeUnit

class PublicKeyRepositoryImpl(
    private val propertyRepository: PropertiesRepository,
    private val publicRepository: PublicRepository,
    private val dataStoreRepository: DataStoreRepository
): PublicKeyRepository {

    private val cacheTtl = TimeUnit.HOURS.toMillis(2)

    override suspend fun updatePublicKey() {

        val publicKeyIdProperty = propertyRepository.getPropertyByKey(PropertyKeyEnum.KYC_GTW_PUBLIC_KEY_ID.name)
        val publicKeyProperty = propertyRepository.getPropertyByKey(PropertyKeyEnum.KYC_GTW_PUBLIC_KEY.name)

        val userPreferences = dataStoreRepository.getUserPreferencesFromDataStore()

        if(publicKeyProperty == null && publicKeyIdProperty == null){

            val publicKeyData: PublicKeyData = publicRepository.getPublicKey()

            val publicKeyIdProperty = LocalProperty(
                propertyName = PropertyKeyEnum.KYC_GTW_PUBLIC_KEY_ID.name,
                propertyValue =  publicKeyData.id
            )

            val publicKeyProperty = LocalProperty(
                propertyName = PropertyKeyEnum.KYC_GTW_PUBLIC_KEY.name,
                propertyValue =  publicKeyData.key
            )

            propertyRepository.insertProperty(publicKeyIdProperty)
            propertyRepository.insertProperty(publicKeyProperty)
            dataStoreRepository.saveToDataStore(userPreferences.copy(
                publicKeyTimestamp = System.currentTimeMillis()
            ))
            Log.i("PublicKeyRepository","SAVED $publicKeyIdProperty")
            Log.i("PublicKeyRepository","SAVED $publicKeyProperty")
        }
        else{

            val publicKeyTimestamp = userPreferences.publicKeyTimestamp
            val expired = (System.currentTimeMillis() - publicKeyTimestamp) > cacheTtl

            Log.d(INTRO_SCREEN_TAG, "Expired $expired")
            if(expired){

                val publicKeyData: PublicKeyData = publicRepository.getPublicKey()
                val publicKeyIdValue = publicKeyIdProperty?.propertyValue

                if(!publicKeyIdValue.equals(publicKeyData.id)){

                    val newPublicKeyIdProperty = LocalProperty(
                        id = publicKeyIdProperty?.id,
                        propertyName = PropertyKeyEnum.KYC_GTW_PUBLIC_KEY_ID.name,
                        propertyValue = publicKeyData.id
                    )

                    val newPublicKeyProperty = LocalProperty(
                        id = publicKeyProperty?.id,
                        propertyName = PropertyKeyEnum.KYC_GTW_PUBLIC_KEY.name,
                        propertyValue = publicKeyData.key
                    )

                    propertyRepository.updateProperty(newPublicKeyIdProperty)
                    propertyRepository.updateProperty(newPublicKeyProperty)
                    dataStoreRepository.saveToDataStore(userPreferences.copy(
                        publicKeyTimestamp = System.currentTimeMillis()
                    ))
                    Log.i("PublicKeyRepository","UPDATED $newPublicKeyIdProperty")
                    Log.i("PublicKeyRepository","UPDATED $newPublicKeyProperty")
                }
            }
        }
    }
}