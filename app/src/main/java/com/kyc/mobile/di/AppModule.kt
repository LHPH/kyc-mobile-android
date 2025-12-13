package com.kyc.mobile.di

import android.content.Context
import com.kyc.mobile.BuildConfig
import com.kyc.mobile.data.local.DataStoreRepositoryImpl
import com.kyc.mobile.data.remote.CustomerApplicationRepositoryImpl
import com.kyc.mobile.data.remote.LoginRepositoryImpl
import com.kyc.mobile.data.remote.api.AuthApi
import com.kyc.mobile.data.remote.api.CustomerApplicationApi
import com.kyc.mobile.data.remote.interceptors.JwtInterceptor
import com.kyc.mobile.dataStore
import com.kyc.mobile.domain.usecase.CustomerApplicationRepository
import com.kyc.mobile.domain.usecase.DataStoreRepository
import com.kyc.mobile.domain.usecase.LoginRepository
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

interface AppModule{

    val authApi: AuthApi
    val customerApplicationApi: CustomerApplicationApi
    val loginRepository: LoginRepository
    val customerApplicationRepository: CustomerApplicationRepository
    val dataStoreRepository: DataStoreRepository
}

class AppModuleImpl(
    private val context: Context
): AppModule {

    val json = Json { ignoreUnknownKeys = true } // Configure Json instance as needed
    val contentType = "application/json".toMediaType()

    override val authApi: AuthApi by lazy{
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(getOkHttpClient())
            .build()
            .create()
    }

    override val customerApplicationApi: CustomerApplicationApi by lazy{
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(getOkHttpClient())
            .build()
            .create()
    }

    private fun getOkHttpClient(): OkHttpClient{

        val client = OkHttpClient.Builder()
            .addInterceptor(jwtInterceptor())
            .addInterceptor(loggingInterceptor())
            .build();
        return client
    }

    private fun loggingInterceptor(): Interceptor{

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return loggingInterceptor;
    }

    private fun jwtInterceptor(): JwtInterceptor{
        return JwtInterceptor(dataStoreRepository)
    }

    override val loginRepository: LoginRepository by lazy{
        LoginRepositoryImpl(authApi,dataStoreRepository)
    }

    override val customerApplicationRepository: CustomerApplicationRepository by lazy{
        CustomerApplicationRepositoryImpl(customerApplicationApi)
    }

    override val dataStoreRepository: DataStoreRepository by lazy{
        DataStoreRepositoryImpl(context.dataStore)
    }
}