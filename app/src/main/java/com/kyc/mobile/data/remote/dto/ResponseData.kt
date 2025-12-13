package com.kyc.mobile.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseData<T>(
    val data: T? = null,
    val error: MessageData? = null
)
