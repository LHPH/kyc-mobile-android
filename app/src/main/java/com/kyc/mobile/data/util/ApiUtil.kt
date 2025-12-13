package com.kyc.mobile.data.util

import com.kyc.mobile.data.remote.dto.ResponseData

class ApiUtil {

    companion object{

        fun <T> checkIfError(response: ResponseData<T>): Boolean{

            return response.error!=null && "ERROR" == response.error.type
        }
    }
}