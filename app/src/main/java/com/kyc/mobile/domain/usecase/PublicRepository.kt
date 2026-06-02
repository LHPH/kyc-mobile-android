package com.kyc.mobile.domain.usecase

interface PublicRepository {

    suspend fun getPublicKey(): String
}