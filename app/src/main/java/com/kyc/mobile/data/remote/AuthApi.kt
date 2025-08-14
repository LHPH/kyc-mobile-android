package com.kyc.mobile.data.remote

import retrofit2.http.POST

interface AuthApi{

    @POST("/user/sign-in")
    suspend fun login()
}