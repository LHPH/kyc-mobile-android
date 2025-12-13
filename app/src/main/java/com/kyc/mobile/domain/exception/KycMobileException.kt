package com.kyc.mobile.domain.exception

import com.kyc.mobile.data.remote.dto.MessageData

data class KycMobileException(
    val errorData: MessageData?,
    val exception: Exception?
): RuntimeException() {
}