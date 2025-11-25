package com.kyc.mobile.data.remote.interceptors

import com.kyc.mobile.data.annotation.TokenAuth
import com.kyc.mobile.domain.usecase.DataStoreRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation

class JwtInterceptor(
    private val dataStoreRepository: DataStoreRepository
): Interceptor  {

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val method = chain.request().tag(Invocation::class.java)!!.method()

        if(method.isAnnotationPresent(TokenAuth::class.java)){

            val newRequest = runBlocking {
                val userPreferences = dataStoreRepository.getUserPreferencesFromDataStore()
                val token = userPreferences.token

                originalRequest.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
            }
            return chain.proceed(newRequest)

        }
        return chain.proceed(originalRequest)
    }
}