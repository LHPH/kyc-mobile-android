package com.kyc.mobile.data.mock.api

import android.content.Context
import com.kyc.mobile.R
import com.kyc.mobile.data.remote.api.AuthApi
import com.kyc.mobile.data.remote.dto.ResponseData
import com.kyc.mobile.data.remote.dto.SessionData
import com.kyc.mobile.data.remote.dto.TokenData
import com.kyc.mobile.data.remote.dto.UserCredentials
import com.kyc.mobile.data.util.decodeToResponseData
import com.kyc.mobile.data.util.readRawResource
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import retrofit2.Response

class MockAuthApi(
    private val context: Context
): AuthApi {

    override suspend fun login(req: UserCredentials): Response<ResponseData<TokenData>> {

        delay(2000)

        val jsonResponse = context.readRawResource(R.raw.mock_successful_response_login)
        val response: ResponseData<TokenData> = Json.decodeToResponseData(jsonResponse);

        return Response.success(response);
    }

    override suspend fun sessionChecking(): Response<ResponseData<SessionData>> {

        delay(500)

        val jsonResponse = context.readRawResource(R.raw.mock_successful_response_session_checking)
        val response: ResponseData<SessionData> = Json.decodeToResponseData(jsonResponse);

        return Response.success(response);
    }

    override suspend fun logout(): Response<Void> {
        delay(500)
        return Response.success(null)
    }
}