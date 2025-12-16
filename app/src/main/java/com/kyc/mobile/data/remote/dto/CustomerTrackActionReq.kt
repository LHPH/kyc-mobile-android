package com.kyc.mobile.data.remote.dto

import com.kyc.mobile.domain.util.AppConstants
import kotlinx.serialization.Serializable

@Serializable
data class CustomerTrackActionReq(
    val customerNumber: Long,
    val channel: Int = AppConstants.CHANNEL_MOBILE,
    val trackId: String,
    val params: Map<String, String>?
)
