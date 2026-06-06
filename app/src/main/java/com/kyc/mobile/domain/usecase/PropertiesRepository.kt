package com.kyc.mobile.domain.usecase

import com.kyc.mobile.domain.model.LocalProperty


interface PropertiesRepository {

    suspend fun insertProperty(property: LocalProperty)

    suspend fun updateProperty(property: LocalProperty)

    suspend fun getPropertyByKey(key: String): LocalProperty?
}