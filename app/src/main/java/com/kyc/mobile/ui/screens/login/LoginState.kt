package com.kyc.mobile.ui.screens.login

import com.kyc.mobile.ui.shared.DisplayState

data class LoginState(
    val username: LoginInput = LoginInput(),
    val password: LoginInput = LoginInput(),
    val loginEnabled: Boolean = false,
    val showPassword: Boolean = false,
    val state: DisplayState = DisplayState.Idle,
    val currentLatitude: Double? = null,
    val currentLongitude: Double? = null
)

data class LoginInput(
    val value: String = "",
    val error: Boolean = false
)