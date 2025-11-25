package com.kyc.mobile.data.repository

import android.util.Log
import com.kyc.mobile.data.remote.AuthApi
import com.kyc.mobile.domain.exception.KycMobileException
import com.kyc.mobile.domain.model.MessageData
import com.kyc.mobile.domain.model.ResponseData
import com.kyc.mobile.domain.model.SessionData
import com.kyc.mobile.domain.model.TokenData
import com.kyc.mobile.domain.model.UserCredentials
import com.kyc.mobile.domain.model.UserPreferences
import com.kyc.mobile.domain.usecase.DataStoreRepository
import com.kyc.mobile.domain.usecase.LoginRepository
import com.kyc.mobile.domain.util.GeneralUtil
import retrofit2.Response

class LoginRepositoryImpl(
    private val authApi: AuthApi,
    private val dataStoreRepository: DataStoreRepository
): LoginRepository {

    override suspend fun login(credentials: UserCredentials) {

        try{
            Log.i("Login", "Authenticate User "+credentials)
            var response: Response<ResponseData<TokenData>> = authApi.login(credentials)

            var payload: String?
            if(response.isSuccessful){
                Log.i("Login", "Successful")
                payload = response.body()?.data?.token!!
                Log.i("Login", "Payload "+payload)
                dataStoreRepository.saveToDataStore(UserPreferences(token = payload))
            }
            else{
                Log.i("Login", "Error")
                payload = response.errorBody()?.string()
                Log.w("Login", "Payload "+payload)

                var kycException: KycMobileException
                if(!GeneralUtil.isNullOrEmpty(payload)){
                    var errorResponse: ResponseData<String> = GeneralUtil.toResponseData(payload, String::class.java)
                    kycException = KycMobileException(errorResponse.error, exception = null)
                }
                else{
                    var errorData = MessageData(message = "Unexpected error in login",time = "")
                    kycException = KycMobileException(errorData, exception = null)
                }

                throw kycException
            }
        }
        catch(ex: KycMobileException){
            throw ex
        }
        catch(ex: Exception){
            Log.e("Login","Exception",ex)
            var errorData = MessageData(message = "Unexpected error in login",time = "")
            throw KycMobileException(errorData, exception = ex)
        }
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

        try{
            val response: Response<ResponseData<SessionData>> = authApi.sessionChecking()
            if(response.isSuccessful){

                val sessionData = response.body()?.data!!
                dataStoreRepository.saveToDataStore(UserPreferences(
                    userId = sessionData.user,
                    customerId = sessionData.owner,
                    role = sessionData.role,
                    name = sessionData.name ?: "TEST"
                ))
                return sessionData
            }
            else{
                var errorData = MessageData(message = "Unexpected error in session",time = "")
                throw KycMobileException(errorData, exception = null)
            }

        }
        catch(ex: KycMobileException){
            throw ex
        }
        catch(ex: Exception){
            Log.e("Login","Exception",ex)
            var errorData = MessageData(message = "Unexpected error in login",time = "")
            throw KycMobileException(errorData, exception = ex)
        }
    }
}