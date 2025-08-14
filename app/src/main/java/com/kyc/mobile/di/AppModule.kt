package com.kyc.mobile.di

import android.content.Context
import com.kyc.mobile.data.remote.AuthApi
import com.kyc.mobile.data.repository.LoginRepositoryImpl
import com.kyc.mobile.domain.usecase.LoginRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

interface AppModule{

    val authApi: AuthApi
    val loginRepository: LoginRepository
}

class AppModuleImpl(
    private val context: Context
): AppModule {

    override val authApi: AuthApi by lazy{
        Retrofit.Builder()
            .baseUrl("http://test.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    override val loginRepository: LoginRepository by lazy{
        LoginRepositoryImpl(authApi)
    }
}