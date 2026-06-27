package com.kyc.mobile.domain.usecase

import java.security.PublicKey

interface PublicKeyRepository {

    suspend fun updatePublicKey()
}