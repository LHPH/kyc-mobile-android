package com.kyc.mobile.data.remote.api

import com.kyc.mobile.data.annotation.TokenAuth
import com.kyc.mobile.data.remote.dto.OfferDataResp
import com.kyc.mobile.data.remote.dto.ResponseData
import com.kyc.mobile.domain.util.AppConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface OfferApi {

    @TokenAuth
    @Headers(AppConstants.HEADER_CHANNEL_MOBILE)
    @GET("/gateway/api/campaigns/customer/{customerNumber}/offers")
    suspend fun getOffers(@Path("customerNumber") customerNumber: Long): Response<ResponseData<List<OfferDataResp>>>

    @TokenAuth
    @Headers(AppConstants.HEADER_CHANNEL_MOBILE)
    @GET("/gateway/api/campaigns/customer/{customerNumber}/offers/{idOffer}")
    suspend fun getDetailOffer(
        @Path("customerNumber") customerNumber: Long,
        @Path("idOffer") idOffer: Long
    ): Response<ResponseData<OfferDataResp>>
}