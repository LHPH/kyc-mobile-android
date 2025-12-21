package com.kyc.mobile.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyc.mobile.data.remote.dto.UserCredentials
import com.kyc.mobile.domain.exception.KycMobileException
import com.kyc.mobile.domain.usecase.LoginRepository
import com.kyc.mobile.domain.util.CredentialsUtil
import com.kyc.mobile.ui.screens.login.LoginAction
import com.kyc.mobile.ui.screens.login.LoginEvent
import com.kyc.mobile.ui.screens.login.LoginInput
import com.kyc.mobile.ui.screens.login.LoginState
import com.kyc.mobile.ui.shared.DisplayState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val LOGIN_TAG = "Login"

class LoginViewModel(
    private val loginRepository: LoginRepository
): ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState())
    val loginState: StateFlow<LoginState> = _loginState

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: LoginAction){
        when(action){
            is LoginAction.OnUsernameChanged ->{
                _loginState.update {
                    var error = CredentialsUtil.isValidUsername(action.value)
                    var loginEnabled = !error && !it.password.error
                    it.copy(loginEnabled = loginEnabled, username = LoginInput(action.value,error))
                }
            }
            is LoginAction.OnPasswordChanged ->{
                _loginState.update{
                    var error = CredentialsUtil.isValidPassword(action.value)
                    var loginEnabled = !error && !it.username.error
                    it.copy(loginEnabled = loginEnabled, password = LoginInput(action.value,error))
                }
            }
            is LoginAction.ShowPassword -> {
                _loginState.update { it.copy(showPassword = !action.value) }
            }
            is LoginAction.OnClickLogin->{
                login()
            }
            is LoginAction.ResetStateToIdle ->{
                _loginState.update {
                    it.copy(state = DisplayState.Idle)
                }
            }
        }
    }

    fun login(){

        Log.i(LOGIN_TAG, "Starting Login process")
        _loginState.update{
            it.copy(state = DisplayState.Loading)
        }
        viewModelScope.launch(Dispatchers.IO) {

            try{
                var credentials = UserCredentials(_loginState.value.username.value,
                    _loginState.value.password.value)

                Log.i(LOGIN_TAG, "Login user")
                loginRepository.login(credentials)

                Log.i(LOGIN_TAG, "Check session")
                loginRepository.sessionChecking()

                Log.i(LOGIN_TAG, "Update view")
                _loginState.update{
                    it.copy(state = DisplayState.Success)
                }
            }
            catch(ex: KycMobileException){
                Log.e(LOGIN_TAG, "Error in login",ex)
                _loginState.update{
                    it.copy(state = DisplayState.Error(ex.errorData!!))
                }
            }
        }
    }
}