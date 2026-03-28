package com.kyc.mobile.data.mock.api

import android.content.Context
import com.kyc.mobile.R
import com.kyc.mobile.data.remote.api.CustomerBillsApi
import com.kyc.mobile.data.remote.dto.CustomerBillsResp
import com.kyc.mobile.data.remote.dto.ResponseData
import com.kyc.mobile.data.util.decodeToResponseData
import com.kyc.mobile.data.util.readRawResource
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import retrofit2.Response

class MockCustomerBillsApi(
    private val context: Context
): CustomerBillsApi {
    override suspend fun getCustomerBills(): Response<ResponseData<List<CustomerBillsResp>>> {

        delay(500)

        val jsonResponse = context.readRawResource(R.raw.mock_successful_response_get_bills)
        val response: ResponseData<List<CustomerBillsResp>> = Json.decodeToResponseData(jsonResponse);

        return Response.success(response);
    }
}