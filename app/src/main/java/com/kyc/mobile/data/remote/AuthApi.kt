package com.kyc.mobile.data.remote

import com.kyc.mobile.data.annotation.TokenAuth
import com.kyc.mobile.domain.model.ResponseData
import com.kyc.mobile.domain.model.SessionData
import com.kyc.mobile.domain.model.TokenData
import com.kyc.mobile.domain.model.UserCredentials
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