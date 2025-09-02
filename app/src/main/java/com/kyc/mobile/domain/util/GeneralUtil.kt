package com.kyc.mobile.domain.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kyc.mobile.domain.model.ResponseData

class GeneralUtil {
    companion object{

        fun <T> stringJsonToModel(json: String?):T {
            var gson = Gson()
            val type = object : TypeToken<T>(){}.type
            return gson.fromJson(json,type);
        }

        fun <T> fromStringJson(json: String?, subtype: Class<T>): ResponseData<T>{
            var gson = Gson()
            val type = TypeToken.getParameterized(ResponseData::class.java,subtype).type
            return gson.fromJson(json,type);
        }

    }
}