package com.kyc.mobile.data.remote.dto

import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.Instant

@Serializable
data class MessageData(
    val code: String = "KYC-MOBILE-000",
    val message: String = "Unexpected Error",
    val type: String = "ERROR",
    val time: Instant = Clock.System.now()
)
