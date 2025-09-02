package com.kyc.mobile.domain.model

data class ResponseData<T>(
    val data: T?,
    val error: MessageData?
)
