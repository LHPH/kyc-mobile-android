package com.kyc.mobile.data.remote

import com.kyc.mobile.domain.model.ResponseData
import com.kyc.mobile.domain.model.TokenData
import com.kyc.mobile.domain.model.UserCredentials
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi{

    @Headers("channel:2")
    @POST("/gateway/api/users/user/sign-in")
    suspend fun login(@Body req: UserCredentials): Response<ResponseData<TokenData>>

    @POST("/gateway/api/users/user/sign-out")
    suspend fun logout(): Response<Void>
}