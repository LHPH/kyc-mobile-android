package com.kyc.mobile.ui.screens.login

import com.kyc.mobile.domain.model.MessageData

sealed class LoginState{

    object Idle: LoginState()
    object Loading: LoginState()
    object Success: LoginState()
    data class Error(val messageData: MessageData): LoginState()
}