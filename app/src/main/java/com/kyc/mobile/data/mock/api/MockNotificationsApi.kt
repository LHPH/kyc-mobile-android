package com.kyc.mobile.data.mock.api

import android.content.Context
import com.kyc.mobile.data.remote.api.NotificationsApi
import com.kyc.mobile.data.remote.dto.NotificationDataResp
import com.kyc.mobile.data.remote.dto.ResponseData
import retrofit2.Response

class MockNotificationsApi(
    private val context: Context
): NotificationsApi {

    override suspend fun getNotifications(): Response<ResponseData<List<NotificationDataResp>>> {
        TODO("Not yet implemented")
    }
}