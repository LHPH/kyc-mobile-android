package com.kyc.mobile.data.util

import com.kyc.mobile.data.remote.dto.ResponseData
import kotlinx.serialization.json.Json
import retrofit2.Response

inline fun <reified T> Response<ResponseData<T>>.processResponseData(): ResponseData<T>{

    return if(this.isSuccessful){
        this.body()!!
    } else{
        Json.decodeToResponseData(this.errorBody()?.string()!!)
    }
}