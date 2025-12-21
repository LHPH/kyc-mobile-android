package com.kyc.mobile.ui.screens.login

import com.kyc.mobile.data.remote.dto.MessageData

sealed interface LoginEvent{

    data class onError(val message: MessageData): LoginEvent
}