package com.kyc.mobile.data.local.repository

import android.util.Log
import com.kyc.mobile.data.local.dao.PropertyDao
import com.kyc.mobile.data.local.entity.PropertyEntity
import com.kyc.mobile.data.local.entity.toModel
import com.kyc.mobile.data.local.entity.toNewEntity
import com.kyc.mobile.data.local.handlingIOResult
import com.kyc.mobile.domain.model.LocalProperty
import com.kyc.mobile.domain.usecase.PropertiesRepository
import java.time.LocalDateTime

class PropertiesRepositoryImpl(
    val propertyDao: PropertyDao
): PropertiesRepository {

    override suspend fun insertProperty(property: LocalProperty) {

        handlingIOResult {
            propertyDao.insertProperty(property.toNewEntity())
        }
            .onSuccess {
                Log.i("PropertiesRepositoryImpl", "Successfully add property")
            }
            .onFailure { throwable ->
                Log.e("PropertiesRepositoryImpl", "Exception", throwable)
            }.getOrThrow()
    }

    override suspend fun updateProperty(property: LocalProperty){

        val resultGetPropertyId = handlingIOResult {
            propertyDao.getPropertyById(property.id ?: 0)
        }
            .onSuccess {
                Log.i("PropertiesRepositoryImpl", "Successfully get property id")
            }
            .onFailure { throwable ->
                Log.e("PropertiesRepositoryImpl", "Exception", throwable)
            }

        val propertyEntity = resultGetPropertyId.getOrThrow()

        if(propertyEntity!=null){

            val updateEntity = propertyEntity.copy(
                propertyKey = property.propertyName,
                propertyValue = property.propertyValue,
                updateAt = LocalDateTime.now()
            )

            handlingIOResult {
                propertyDao.updateProperty(updateEntity)
            }
                .getOrThrow()
        }
        else{
            insertProperty(property)
        }
    }

    override suspend fun getPropertyById(id: Int): LocalProperty? {

        val result: Result<PropertyEntity?>  = handlingIOResult {
            propertyDao.getPropertyById(id)
        }
            .onSuccess {
                Log.i("PropertiesRepositoryImpl", "Successfully fetch property by id")
            }
            .onFailure { throwable ->
                Log.e("PropertiesRepositoryImpl", "Exception", throwable)
            }

        val data = result.getOrThrow()
        return data?.toModel()
    }

    override suspend fun getPropertyByKey(key: String): LocalProperty? {

        val result: Result<PropertyEntity?>  = handlingIOResult {
            propertyDao.getPropertyByPropertyKey(key)
        }
            .onSuccess {
                Log.i("PropertiesRepositoryImpl", "Successfully fetch property")
            }
            .onFailure { throwable ->
                Log.e("PropertiesRepositoryImpl", "Exception", throwable)
            }

        val data = result.getOrThrow()
        return data?.toModel()
    }

    override suspend fun checkPropertyByKey(key: String): Boolean {
        return getPropertyByKey(key) != null
    }
}