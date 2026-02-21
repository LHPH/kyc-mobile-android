package com.kyc.mobile.domain.model

data class CustomerBill(
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
