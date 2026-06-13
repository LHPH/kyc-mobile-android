package com.kyc.mobile.data.local

import com.kyc.mobile.data.remote.dto.MessageData
import com.kyc.mobile.domain.exception.KycMobileException

suspend inline fun <reified T> handlingIOResult(
    crossinline callback: suspend () -> T
): Result<T> {

    try{
        val result = callback()
        return Result.success(result)
    }
    catch(ex: Exception){
        val errorData = MessageData(message = "Unexpected error",time = "")
        return Result.failure(KycMobileException(errorData, exception = ex))
    }
}