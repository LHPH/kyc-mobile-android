package com.kyc.mobile.data.remote.dto

import com.kyc.mobile.domain.model.PublicKeyData
import kotlinx.serialization.Serializable

@Serializable
data class PublicKeyResponse(
    val kid: String,
    val key: String,
)

fun PublicKeyResponse.toModel() = PublicKeyData(kid,key)
