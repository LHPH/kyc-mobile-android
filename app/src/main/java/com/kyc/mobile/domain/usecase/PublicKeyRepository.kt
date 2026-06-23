package com.kyc.mobile.domain.usecase

import java.security.PublicKey

interface PublicKeyRepository {

    suspend fun updatePublicKey()

    suspend fun getPublicKey(): PublicKey
}