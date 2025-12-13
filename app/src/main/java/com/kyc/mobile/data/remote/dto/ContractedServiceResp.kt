package com.kyc.mobile.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ContractedServiceResp(
    val folio: Int,
    val serviceSequential: Int,
    val serviceType: Int,
    val service: String,
    val cost: Double,
    val idChannel: Int,
    val channel: String,
    val idOffice: Int,
    val office: String,
    val active: Boolean,
    val promotions: ContractedServicePromotion,
    val creationDate: String,
    val modificationDate: String
)

@Serializable
data class ContractedServicePromotion(
    val acceptPromotions: Boolean,
    val acceptPromotionsEmail: Boolean,
    val acceptPromotionsCellPhone: Boolean
)