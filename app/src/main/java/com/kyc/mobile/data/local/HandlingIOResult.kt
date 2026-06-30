package com.kyc.mobile.data.local

import com.kyc.mobile.data.remote.dto.MessageData
import com.kyc.mobile.domain.exception.KycMobileException
import com.kyc.mobile.domain.util.LocalErrorEnum

suspend inline fun <reified T> handlingIOResult(
    crossinline callback: suspend () -> T
): Result<T> {

    try{
        val result = callback()
        return Result.success(result)
    }
    catch(ex: Exception){
        val errorData = LocalErrorEnum.LOCAL_DATABASE.toMessageData()
        return Result.failure(KycMobileException(errorData, exception = ex))
    }
}