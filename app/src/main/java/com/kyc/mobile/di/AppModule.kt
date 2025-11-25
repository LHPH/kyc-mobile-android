package com.kyc.mobile.di

import android.content.Context
import com.kyc.mobile.data.local.DataStoreRepositoryImpl
import com.kyc.mobile.data.remote.AuthApi
import com.kyc.mobile.data.remote.interceptors.JwtInterceptor
import com.kyc.mobile.data.repository.LoginRepositoryImpl
import com.kyc.mobile.dataStore
import com.kyc.mobile.domain.usecase.DataStoreRepository
import com.kyc.mobile.domain.usecase.LoginRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

interface AppModule{

    val authApi: AuthApi
    val loginRepository: LoginRepository
    val dataStoreRepository: DataStoreRepository
}

class AppModuleImpl(
    private val context: Context
): AppModule {

    override val authApi: AuthApi by lazy{
        Retrofit.Builder()
            .baseUrl("http://192.168.100.61:10000")
            .addConverterFactory(GsonConverterFactory.create())
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

    override val dataStoreRepository: DataStoreRepository by lazy{
        DataStoreRepositoryImpl(context.dataStore)
    }
}