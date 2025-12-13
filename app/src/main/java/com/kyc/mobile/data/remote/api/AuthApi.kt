package com.kyc.mobile.data.remote.api

import com.kyc.mobile.data.annotation.TokenAuth
import com.kyc.mobile.data.remote.dto.ResponseData
import com.kyc.mobile.data.remote.dto.SessionData
import com.kyc.mobile.data.remote.dto.TokenData
import com.kyc.mobile.data.remote.dto.UserCredentials
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi{

    @Headers("channel:2")
    @POST("/gateway/api/users/user/sign-in")
    suspend fun login(@Body req: UserCredentials): Response<ResponseData<TokenData>>

    @Headers("channel:2")
    @TokenAuth
    @GET("/gateway/api/users/user/session-checking")
    suspend fun sessionChecking(): Response<ResponseData<SessionData>>

    @Headers("channel:2")
    @TokenAuth
    @POST("/gateway/api/users/user/sign-out")
    suspend fun logout(): Response<Void>
}