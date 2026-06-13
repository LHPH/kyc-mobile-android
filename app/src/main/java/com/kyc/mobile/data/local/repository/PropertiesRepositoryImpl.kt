package com.kyc.mobile.data.local.repository

import com.kyc.mobile.data.local.dao.PropertyDao
import com.kyc.mobile.data.local.entity.toModel
import com.kyc.mobile.data.local.entity.toNewEntity
import com.kyc.mobile.domain.model.LocalProperty
import com.kyc.mobile.domain.usecase.PropertiesRepository
import java.time.LocalDateTime

class PropertiesRepositoryImpl(
    val propertyDao: PropertyDao
): PropertiesRepository {

    override suspend fun insertProperty(property: LocalProperty) {
        propertyDao.insertProperty(property.toNewEntity())
    }

    override suspend fun updateProperty(property: LocalProperty){

        propertyDao.getPropertyById(property.id)?.let {

            val updateEntity = it.copy(
                propertyKey = property.propertyName,
                propertyValue = property.propertyValue,
                updateAt = LocalDateTime.now()
            )
            propertyDao.updateProperty(updateEntity)
        }
    }

    override suspend fun getPropertyByKey(key: String): LocalProperty? {

        return propertyDao.getPropertyByPropertyKey(key)?.toModel()
    }

    override suspend fun checkPropertyByKey(key: String): Boolean {
        return getPropertyByKey(key) != null
    }
}