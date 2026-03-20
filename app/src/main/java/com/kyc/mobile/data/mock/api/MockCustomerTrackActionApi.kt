package com.kyc.mobile.data.mock.api

import android.content.Context
import com.kyc.mobile.data.remote.api.CustomerTrackActionApi
import com.kyc.mobile.data.remote.dto.CustomerTrackActionReq
import com.kyc.mobile.data.remote.dto.ResponseData
import kotlinx.coroutines.delay
import retrofit2.Response

class MockCustomerTrackActionApi(
    private val context: Context
): CustomerTrackActionApi {

    override suspend fun trackAction(req: CustomerTrackActionReq): Response<ResponseData<Boolean>> {

        delay(500)
        return Response.success(ResponseData(true))
    }
}