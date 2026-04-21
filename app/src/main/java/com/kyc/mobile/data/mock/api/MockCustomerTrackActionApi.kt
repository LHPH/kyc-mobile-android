package com.kyc.mobile.data.mock.api

import android.content.Context
import android.util.Log
import com.kyc.mobile.data.remote.api.CustomerTrackActionApi
import com.kyc.mobile.data.remote.dto.CustomerTrackActionReq
import com.kyc.mobile.data.remote.dto.ResponseData
import kotlinx.coroutines.delay
import retrofit2.Response

const val MOCK_CUSTOMER_TRACK = "MockCustomerTrackActionApi"
class MockCustomerTrackActionApi(
    private val context: Context
): CustomerTrackActionApi {

    override suspend fun trackAction(req: CustomerTrackActionReq): Response<ResponseData<Boolean>> {

        Log.d(MOCK_CUSTOMER_TRACK, "trackAction Request: $req")
        delay(500)
        Log.d(MOCK_CUSTOMER_TRACK, "trackAction Response: {\"data\":true}")
        return Response.success(ResponseData(true))
    }
}