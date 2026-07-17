package com.kyc.mobile.data.mock.api

import android.content.Context
import android.util.Log
import com.kyc.mobile.BuildConfig
import com.kyc.mobile.R
import com.kyc.mobile.data.remote.api.CustomerBillsApi
import com.kyc.mobile.data.remote.dto.CustomerBillsResp
import com.kyc.mobile.data.remote.dto.ResponseData
import com.kyc.mobile.data.util.JsonDefaults
import com.kyc.mobile.data.util.decodeToResponseData
import com.kyc.mobile.data.util.readRawResource
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

const val MOCK_CUSTOMER_BILLS_API = "MockCustomerBillsApi"
class MockCustomerBillsApi(
    private val context: Context
): CustomerBillsApi {

    @Suppress("KotlinConstantConditions")
    override suspend fun getCustomerBills(): Response<ResponseData<List<CustomerBillsResp>>> {

        delay(500)

        val jsonResponse = if(BuildConfig.MOCK_ERROR_RESPONSE_GET_CUSTOMER_BILLS){
            context.readRawResource(R.raw.mock_error_response)
        }
        else{
            context.readRawResource(R.raw.mock_successful_response_get_bills)
        }
        Log.d(MOCK_CUSTOMER_BILLS_API, "getCustomerBills Response: $jsonResponse")
        val response: ResponseData<List<CustomerBillsResp>> = Json.decodeToResponseData(jsonResponse);

        return if(BuildConfig.MOCK_ERROR_RESPONSE_GET_CUSTOMER_BILLS){
            Response.error(500,jsonResponse.toResponseBody(JsonDefaults.contentType))
        }
        else{
            Response.success(response)
        }
    }
}