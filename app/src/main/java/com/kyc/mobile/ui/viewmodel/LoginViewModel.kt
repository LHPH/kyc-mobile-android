package com.kyc.mobile.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyc.mobile.data.remote.dto.SessionData
import com.kyc.mobile.data.remote.dto.UserCredentials
import com.kyc.mobile.domain.exception.KycMobileException
import com.kyc.mobile.domain.model.CustomerAction
import com.kyc.mobile.domain.usecase.AnalyticsRepository
import com.kyc.mobile.domain.usecase.CustomerTrackActionRepository
import com.kyc.mobile.domain.usecase.LoginRepository
import com.kyc.mobile.domain.util.CredentialsUtil
import com.kyc.mobile.domain.util.GeneralUtil
import com.kyc.mobile.domain.util.TrackIdEnum
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
    private val appContext: Context,
    private val loginRepository: LoginRepository,
    private val customerTrackActionRepository: CustomerTrackActionRepository,
    private val analyticsManager: AnalyticsRepository
): ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState())
    val loginState: StateFlow<LoginState> = _loginState

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: LoginAction){
        when(action){
            is LoginAction.OnUsernameChanged ->{
                _loginState.update {
                    val error = !CredentialsUtil.isValidUsername(action.value)
                    val errorPassword = !CredentialsUtil.isValidPassword(it.password.value)
                    val loginEnabled = !error && !errorPassword
                    it.copy(loginEnabled = loginEnabled, username = LoginInput(action.value,error))
                }
            }
            is LoginAction.OnPasswordChanged ->{
                _loginState.update{
                    val error = !CredentialsUtil.isValidPassword(action.value)
                    val errorUsername = !CredentialsUtil.isValidUsername(it.username.value)
                    val loginEnabled = !error && !errorUsername
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
        analyticsManager.logEvent("login_app", mapOf("1" to "23"))
        _loginState.update{
            it.copy(state = DisplayState.Loading)
        }
        viewModelScope.launch(Dispatchers.IO) {

            try{
                val credentials = UserCredentials(_loginState.value.username.value,
                    _loginState.value.password.value)

                Log.i(LOGIN_TAG, "Login user")
                loginRepository.login(credentials)

                Log.i(LOGIN_TAG, "Check session")
                val sessionData = loginRepository.sessionChecking()
                registerAction(sessionData)

                Log.i(LOGIN_TAG, "Update view")
                _loginState.update{
                    it.copy(state = DisplayState.Success)
                }
            }
            catch(ex: KycMobileException){
                Log.e(LOGIN_TAG, "Error in login",ex)
                analyticsManager.logException(screenName = "Login", ex)
                _loginState.update{
                    it.copy(state = DisplayState.Error(ex.errorData!!))
                }
            }
        }
    }

    private suspend fun registerAction(sessionData: SessionData){

        val params = HashMap<String,String>()
        params["device"]= GeneralUtil.getDeviceId(appContext)
        params["ip"] = "127.0.0.2"
        params["longitude"] = "123456789"
        params["latitude"] = "987654321"
        params["category"] = "Auth"
        params["event"] = "Login"

        val action = CustomerAction(customerNumber = sessionData.owner,
            trackId = TrackIdEnum.LOGIN.id.toString(), params )

        customerTrackActionRepository.registerAction(action)
    }
}