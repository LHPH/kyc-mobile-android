package com.kyc.mobile.di

import com.kyc.mobile.BuildConfig
import com.kyc.mobile.data.remote.api.AuthApi
import com.kyc.mobile.data.remote.api.CustomerApplicationApi
import com.kyc.mobile.data.remote.api.CustomerBillsApi
import com.kyc.mobile.data.remote.api.CustomerTrackActionApi
import com.kyc.mobile.data.remote.api.NotificationsApi
import com.kyc.mobile.data.remote.api.OfferApi
import com.kyc.mobile.data.remote.interceptors.JwtInterceptor
import com.kyc.mobile.data.util.JsonDefaults
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

interface NetworkModule {
    val authApi: AuthApi
    val customerApplicationApi: CustomerApplicationApi
    val customerTrackActionApi: CustomerTrackActionApi
    val notificationApi: NotificationsApi
    val offerApi: OfferApi
    val customerBillsApi: CustomerBillsApi
}

class NetworkModuleImpl(
    private val dataStoreModule: DataStoreModule
): NetworkModule{

    val contentType = "application/json".toMediaType()

    override val authApi: AuthApi by lazy{
            getRetrofit()
            .create()
    }

    override val customerApplicationApi: CustomerApplicationApi by lazy{
            getRetrofit()
            .create()
    }

    override val customerTrackActionApi: CustomerTrackActionApi by lazy{
            getRetrofit()
            .create()
    }

    override val notificationApi: NotificationsApi by lazy{
            getRetrofit()
            .create()
    }

    override val offerApi: OfferApi by lazy{
            getRetrofit()
            .create()
    }

    override val customerBillsApi: CustomerBillsApi by lazy{
            getRetrofit()
            .create()
    }

    private fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(JsonDefaults.instance.asConverterFactory(contentType))
            .client(getOkHttpClient())
            .build()
    }

    private fun getOkHttpClient(): OkHttpClient{

        val client = OkHttpClient.Builder()
            .addInterceptor(jwtInterceptor())
            .addInterceptor(loggingInterceptor())
            .build()
        return client
    }

    private fun loggingInterceptor(): Interceptor{

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            if(BuildConfig.LOG_ENABLED){
                level = HttpLoggingInterceptor.Level.BODY
            }
            else{
                level = HttpLoggingInterceptor.Level.NONE
            }
        }
        return loggingInterceptor;
    }

    private fun jwtInterceptor(): JwtInterceptor{
        return JwtInterceptor(dataStoreModule.dataStoreRepository)
    }
}