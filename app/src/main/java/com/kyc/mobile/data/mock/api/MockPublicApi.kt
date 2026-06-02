package com.kyc.mobile.data.mock.api

import android.content.Context
import android.util.Log
import com.kyc.mobile.R
import com.kyc.mobile.data.remote.api.PublicApi
import com.kyc.mobile.data.remote.dto.ResponseData
import com.kyc.mobile.data.util.decodeToResponseData
import com.kyc.mobile.data.util.readRawResource
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import retrofit2.Response

const val MOCK_PUBLIC_API = "MockPublicApi"
class MockPublicApi(
    private val context: Context
): PublicApi{

    override suspend fun getPublicKey(): Response<ResponseData<String>> {

        delay(500)

        val jsonResponse = context.readRawResource(R.raw.mock_successful_response_get_public_key)
        Log.d(MOCK_PUBLIC_API, "getPublicKey $jsonResponse")
        val responseData: ResponseData<String> = Json.decodeToResponseData(jsonResponse);
        return Response.success(responseData);
    }
}