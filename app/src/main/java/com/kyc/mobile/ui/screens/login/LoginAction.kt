package com.kyc.mobile.ui.screens.login

sealed interface LoginAction{

    data class OnUsernameChanged(val value: String): LoginAction
    data class OnPasswordChanged(val value: String): LoginAction
    data class ShowPassword(val value: Boolean): LoginAction
    data object OnClickLogin: LoginAction
    data object ResetStateToIdle: LoginAction
}