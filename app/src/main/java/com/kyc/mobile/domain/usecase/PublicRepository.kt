package com.kyc.mobile.domain.usecase

import com.kyc.mobile.domain.model.PublicKeyData

interface PublicRepository {

    suspend fun getPublicKey(): PublicKeyData
}