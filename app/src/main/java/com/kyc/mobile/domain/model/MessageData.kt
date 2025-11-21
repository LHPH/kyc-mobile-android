package com.kyc.mobile.domain.model

data class MessageData(
    val code: String = "KYC-MOBILE-000",
    val message: String = "Unexpected Error",
    val type: String = "ERROR",
    val time: String
)
