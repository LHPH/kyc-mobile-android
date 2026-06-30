package com.kyc.mobile.domain.util

import com.kyc.mobile.data.remote.dto.MessageData

enum class LocalErrorEnum(
    val code: String,
    val message: String,
    val type: String
){

    UNEXPECTED("000","A unexpected error has occurred","ERROR"),
    LOCAL_DATABASE("001","Cannot retrieved needed configurations","ERROR");

    fun toMessageData(): MessageData{

        return MessageData(
            code = "KYC-MOBILE-${this.code}",
            message = this.message,
            type = this.type
        )
    }
}