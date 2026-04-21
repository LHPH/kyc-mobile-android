package com.kyc.mobile.data.mock.api

import android.content.Context
import android.util.Log
import com.kyc.mobile.R
import com.kyc.mobile.data.remote.api.NotificationsApi
import com.kyc.mobile.data.remote.dto.NotificationDataResp
import com.kyc.mobile.data.remote.dto.ResponseData
import com.kyc.mobile.data.util.decodeToResponseData
import com.kyc.mobile.data.util.readRawResource
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import retrofit2.Response

const val MOCK_NOTIFICATIONS_API = "MockNotificationsApi"
class MockNotificationsApi(
    private val context: Context
): NotificationsApi {

    override suspend fun getNotifications(): Response<ResponseData<List<NotificationDataResp>>> {

        delay(500)

        val jsonResponse = context.readRawResource(R.raw.mock_successful_response_get_notifications)
        Log.d(MOCK_NOTIFICATIONS_API, "getNotifications Response: $jsonResponse")
        val response: ResponseData<List<NotificationDataResp>> = Json.decodeToResponseData(jsonResponse);

        return Response.success(response);
    }
}