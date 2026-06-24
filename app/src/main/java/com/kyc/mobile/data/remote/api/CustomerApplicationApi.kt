package com.kyc.mobile.data.remote.api

import com.kyc.mobile.data.annotation.Crypt
import com.kyc.mobile.data.annotation.TokenAuth
import com.kyc.mobile.data.remote.dto.ContractedServiceResp
import com.kyc.mobile.data.remote.dto.ResponseData
import com.kyc.mobile.domain.util.AppConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface CustomerApplicationApi {

    @Crypt
    @TokenAuth
    @Headers(AppConstants.HEADER_CHANNEL_MOBILE)
    @GET("/gateway/api/customer-application/contracted-services")
    suspend fun contractedServices(): Response<ResponseData<List<ContractedServiceResp>>>
}