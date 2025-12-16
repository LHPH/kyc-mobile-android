package com.kyc.mobile.data.remote.api

import com.kyc.mobile.data.annotation.TokenAuth
import com.kyc.mobile.data.remote.dto.NotificationDataResp
import com.kyc.mobile.data.remote.dto.ResponseData
import com.kyc.mobile.domain.util.AppConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface NotificationsApi {

    @TokenAuth
    @Headers(AppConstants.HEADER_CHANNEL_MOBILE)
    @GET("/gateway/api/notifications/notifications/")
    suspend fun getNotifications(): Response<ResponseData<List<NotificationDataResp>>>
}