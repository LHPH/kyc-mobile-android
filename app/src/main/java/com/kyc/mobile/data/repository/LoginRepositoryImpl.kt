package com.kyc.mobile.data.repository

import android.util.Log
import com.kyc.mobile.data.remote.AuthApi
import com.kyc.mobile.domain.exception.KycMobileException
import com.kyc.mobile.domain.model.MessageData
import com.kyc.mobile.domain.model.ResponseData
import com.kyc.mobile.domain.model.UserCredentials
import com.kyc.mobile.domain.usecase.LoginRepository
import com.kyc.mobile.domain.util.GeneralUtil
import retrofit2.Response

class LoginRepositoryImpl(
    private val authApi: AuthApi
): LoginRepository {

    override suspend fun login(credentials: UserCredentials) {

        try{
            Log.i("Login", "Authenticate User "+credentials)
            //delay(2000)
            var response: Response<ResponseData<String>> = authApi.login(credentials);
            var payload: String?
            if(response.isSuccessful){
                Log.i("Login", "Successful")
                payload = response.body()?.data
                Log.i("Login", "Payload "+payload);
            }
            else{
                Log.i("Login", "Error")
                payload = response.errorBody()?.string();
                Log.w("Login", "Payload "+payload);

                var errorResponse: ResponseData<String> = GeneralUtil.fromStringJson(payload, String::class.java);
                throw KycMobileException(errorResponse.error, exception = null);
            }
        }
        catch(ex: KycMobileException){
            throw ex;
        }
        catch(ex: Exception){
            Log.e("Login","Exception",ex)
            var errorData = MessageData("ERR00","Error in Login","ERROR","");
            throw KycMobileException(errorData, exception = ex);
        }
    }
}