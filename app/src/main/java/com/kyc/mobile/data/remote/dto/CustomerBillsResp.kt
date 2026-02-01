package com.kyc.mobile.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CustomerBillsResp(
    val id: Long,
    val taxes: Double,
    val subtotal: Double,
    val total: Double,
    val settled: Boolean,
    val status: String,
    val issueDate: String,
    val billingStartDate: String,
    val billingFinishDate: String,
    val paymentDueDate: String,
    val settlementDate: String? = null
)
