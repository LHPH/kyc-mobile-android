package com.kyc.mobile.data.remote.api

import com.kyc.mobile.data.annotation.TokenAuth
import com.kyc.mobile.data.remote.dto.CustomerTrackActionReq
import com.kyc.mobile.data.remote.dto.ResponseData
import com.kyc.mobile.domain.util.AppConstants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface CustomerTrackActionApi {

    @TokenAuth
    @Headers(AppConstants.HEADER_CHANNEL_MOBILE)
    @POST("/gateway/api/tracking/customers/v1/action")
    suspend fun trackAction(@Body req: CustomerTrackActionReq): Response<ResponseData<Boolean>>
}