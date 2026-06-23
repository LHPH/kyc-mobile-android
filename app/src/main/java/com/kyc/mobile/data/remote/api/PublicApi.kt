package com.kyc.mobile.data.remote.api

import com.kyc.mobile.data.remote.dto.PublicKeyResponse
import com.kyc.mobile.data.remote.dto.ResponseData
import com.kyc.mobile.domain.util.AppConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface PublicApi {

    @Headers(AppConstants.HEADER_CHANNEL_MOBILE)
    @GET("/gateway/public/public-key")
    suspend fun  getPublicKey(): Response<ResponseData<PublicKeyResponse>>
}