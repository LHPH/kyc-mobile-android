package com.kyc.mobile.domain.model


data class CustomerAction(
    val customerNumber: Long,
    val trackId: String,
    val params: Map<String, String>?
)
