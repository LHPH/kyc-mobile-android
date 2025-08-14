package com.kyc.mobile.ui.screens.login

sealed class LoginState{

    object Idle: LoginState()
    object Loading: LoginState()
    object Success: LoginState()
    data class Error(val message: String): LoginState()
}