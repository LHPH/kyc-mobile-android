package com.kyc.mobile.data.remote

import android.util.Log
import com.kyc.mobile.data.remote.api.AuthApi
import com.kyc.mobile.data.remote.dto.MessageData
import com.kyc.mobile.data.remote.dto.ResponseData
import com.kyc.mobile.data.remote.dto.SessionData
import com.kyc.mobile.data.remote.dto.TokenData
import com.kyc.mobile.data.remote.dto.UserCredentials
import com.kyc.mobile.data.util.ApiUtil
import com.kyc.mobile.data.util.processResponseData
import com.kyc.mobile.domain.exception.KycMobileException
import com.kyc.mobile.domain.model.UserPreferences
import com.kyc.mobile.domain.usecase.DataStoreRepository
import com.kyc.mobile.domain.usecase.LoginRepository
import retrofit2.Response

class LoginRepositoryImpl(
    private val authApi: AuthApi,
    private val dataStoreRepository: DataStoreRepository
): LoginRepository {

    override suspend fun login(credentials: UserCredentials) {

        try{
            Log.i("Login", "Authenticate User "+credentials)
            var response: Response<ResponseData<TokenData>> = authApi.login(credentials)

            val result: ResponseData<TokenData>  = response.processResponseData()
            val error = ApiUtil.checkIfError(result)

            if(!error){
                Log.i("Login", "Successful")
                val token = result.data?.token!!
                dataStoreRepository.saveToDataStore(UserPreferences(token = token))
            }
            else{
                val kycException = KycMobileException(result.error, exception = null)
                Log.e("Login", "Error $kycException")
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
            val result: ResponseData<SessionData>  = response.processResponseData()
            val error = ApiUtil.checkIfError(result)

            if(!error){
                val sessionData = result.data!!
                dataStoreRepository.saveToDataStore(UserPreferences(
                    userId = sessionData.user,
                    customerId = sessionData.owner,
                    role = sessionData.role,
                    name = sessionData.name!!
                ))
                return sessionData
            }
            else{
                val kycException = KycMobileException(result.error, exception = null)
                Log.e("Login", "Error $kycException")
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
}