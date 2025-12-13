package com.kyc.mobile.domain.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kyc.mobile.data.remote.dto.ResponseData

class GeneralUtil {

    companion object{

        var gson = Gson()

        fun <T> toResponseData(json: String?, subtype: Class<T>): ResponseData<T>{
            val type = TypeToken.getParameterized(ResponseData::class.java,subtype).type
            return gson.fromJson(json,type);
        }

        fun isNullOrEmpty(value: String?): Boolean{
            return value == null || value.isEmpty()
        }

    }
}