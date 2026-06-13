package com.kyc.mobile.domain.usecase

import com.kyc.mobile.domain.model.LocalProperty


interface PropertiesRepository {

    suspend fun insertProperty(property: LocalProperty)

    suspend fun updateProperty(property: LocalProperty)

    suspend fun getPropertyById(id: Int): LocalProperty?

    suspend fun getPropertyByKey(key: String): LocalProperty?

    suspend fun checkPropertyByKey(key: String): Boolean
}