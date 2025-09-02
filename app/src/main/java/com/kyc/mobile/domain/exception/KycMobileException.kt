package com.kyc.mobile.domain.exception

import com.kyc.mobile.domain.model.MessageData

data class KycMobileException(
    val errorData: MessageData?,
    val exception: Exception?
): RuntimeException() {
}