package com.kyc.mobile.data.mock.api

import android.content.Context
import com.kyc.mobile.R
import com.kyc.mobile.data.remote.api.CustomerApplicationApi
import com.kyc.mobile.data.remote.dto.ContractedServiceResp
import com.kyc.mobile.data.remote.dto.ResponseData
import com.kyc.mobile.data.util.decodeToResponseData
import com.kyc.mobile.data.util.readRawResource
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import retrofit2.Response

class MockCustomerApplicationApi(
    private val context: Context
): CustomerApplicationApi{

    override suspend fun contractedServices(): Response<ResponseData<List<ContractedServiceResp>>> {

        delay(500)

        val jsonResponse = context.readRawResource(R.raw.mock_successful_response_get_customer_applications)
        val response: ResponseData<List<ContractedServiceResp>> = Json.decodeToResponseData(jsonResponse);

        return Response.success(response);
    }

}