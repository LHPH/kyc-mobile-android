package com.kyc.mobile.data.repository

import android.util.Log
import com.kyc.mobile.data.remote.AuthApi
import com.kyc.mobile.domain.model.UserCredentials
import com.kyc.mobile.domain.usecase.LoginRepository
import kotlinx.coroutines.delay

class LoginRepositoryImpl(
    private val authApi: AuthApi
): LoginRepository {

    override suspend fun login(credentials: UserCredentials) {

        try{
            Log.i("Login", "Authenticate User")
            delay(2000)
        }
        catch(ex: Exception){

        }
    }
}