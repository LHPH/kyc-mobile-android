package com.kyc.mobile.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class NotificationDataResp(
    val message: String,
    val event: String,
    val date: String,
    val issuer: String,
    val recipient: String,
    val channel: String
)