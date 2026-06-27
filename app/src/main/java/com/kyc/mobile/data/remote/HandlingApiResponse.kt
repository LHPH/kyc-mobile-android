package com.kyc.mobile.data.remote

import com.kyc.mobile.data.remote.dto.MessageData
import com.kyc.mobile.data.remote.dto.ResponseData
import com.kyc.mobile.data.util.ApiUtil
import com.kyc.mobile.data.util.processResponseData
import com.kyc.mobile.domain.exception.KycMobileException
import retrofit2.Response

suspend inline fun <reified T> handlingApiResponse(
     crossinline callback: suspend () -> Response<ResponseData<T>>,
): Result<ResponseData<T>>{

    try{

        val response:Response<ResponseData<T>> = callback()
        val result:ResponseData<T> = response.processResponseData()
        val error = ApiUtil.checkIfError(result)

        if(error){
            val kycException = KycMobileException(result.error, exception = null)
            return Result.failure(kycException)
        }
        return Result.success(result);
    }
    catch (ex: Exception){

        val errorData = MessageData(message = "Unexpected error")
        return Result.failure(KycMobileException(errorData, exception = ex))
    }
}