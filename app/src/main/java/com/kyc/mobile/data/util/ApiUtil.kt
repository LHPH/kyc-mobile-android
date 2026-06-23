package com.kyc.mobile.data.util

import com.kyc.mobile.data.remote.dto.ResponseData
import com.kyc.mobile.domain.util.AppConstants.BEARER_TOKEN
import com.kyc.mobile.domain.util.AppConstants.HEADER_AUTHORIZATION
import okhttp3.Request

class ApiUtil {

    companion object{

        fun <T> checkIfError(response: ResponseData<T>): Boolean{

            return response.error!=null && "ERROR" == response.error.type
        }

        fun getAuthorization(req: Request): String{
            return extractAuthToken(req.header(HEADER_AUTHORIZATION))
        }

        fun extractAuthToken(value: String?): String{

            if(value!=null){

                return value
                    .replace(BEARER_TOKEN,"")
                    .trim()
            }
            return ""
        }
    }
}