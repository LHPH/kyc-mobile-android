package com.kyc.mobile.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class OfferDataResp(
    val id: Long,
    val customerNumber: Long,
    val customerEmail: String,
    val offerName: String,
    val offerDescription: String,
    val discount: Double,
    val reward: String,
    val startDate: String,
    val finishDate: String,
    val status: String,
    val termAndConditionsLink: String
)