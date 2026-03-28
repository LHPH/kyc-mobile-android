package com.kyc.mobile.data.remote.repository

import android.util.Log
import com.kyc.mobile.data.remote.api.AuthApi
import com.kyc.mobile.data.remote.dto.ResponseData
import com.kyc.mobile.data.remote.dto.SessionData
import com.kyc.mobile.data.remote.dto.TokenData
import com.kyc.mobile.data.remote.dto.UserCredentials
import com.kyc.mobile.data.remote.handlingApiResponse
import com.kyc.mobile.domain.model.UserPreferences
import com.kyc.mobile.domain.usecase.DataStoreRepository
import com.kyc.mobile.domain.usecase.LoginRepository

class LoginRepositoryImpl(
    private val authApi: AuthApi,
    private val dataStoreRepository: DataStoreRepository
): LoginRepository {

    override suspend fun login(credentials: UserCredentials) {

        Log.i("Login", "Authenticate User $credentials")
        val result = handlingApiResponse {
            authApi.login(credentials)
        }
            .onSuccess {
                Log.i("LoginRepositoryImpl", "Successful")
            }
            .onFailure { throwable ->
                Log.e("LoginRepositoryImpl","Exception",throwable)
            }

        val responseData: ResponseData<TokenData>  = result.getOrThrow()
        val token = responseData.data?.token!!
        dataStoreRepository.saveToDataStore(UserPreferences(token = token))
    }

    override suspend fun logout(){

        try{
            authApi.logout()
            dataStoreRepository.saveToDataStore(UserPreferences())
        }
        catch(ex: Exception){
            Log.e("Login","Exception",ex)
        }
    }

    override suspend fun sessionChecking(): SessionData {

        val result = handlingApiResponse {
            authApi.sessionChecking()
        }
            .onSuccess {
                Log.i("LoginRepositoryImpl","Successfully check session")
            }
            .onFailure { throwable ->
                Log.e("LoginRepositoryImpl","Exception",throwable)
            }

        val responseService: ResponseData<SessionData> = result.getOrThrow()

        val sessionData = responseService.data!!
        dataStoreRepository.saveToDataStore(UserPreferences(
            userId = sessionData.user,
            customerId = sessionData.owner,
            role = sessionData.role,
            name = sessionData.name!!
        ))
        return sessionData;
    }
}