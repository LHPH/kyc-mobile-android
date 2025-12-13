package com.kyc.mobile.domain.model

data class CustomerContractService(
    val folio: Int,
    val service: String,
    val cost: Double,
    val active: Boolean,
    val acceptPromotions: Boolean,
    val acceptPromotionsEmail: Boolean,
    val acceptPromotionsCellPhone: Boolean,
    val creationDate: String
)


