package com.kyc.mobile.domain.usecase

import javax.crypto.SecretKey

interface KeystoreRepository {

    fun getSecretKey(entry: String): SecretKey
}