package com.kyc.mobile.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.kyc.mobile.data.remote.dto.SessionData
import com.kyc.mobile.data.remote.dto.UserCredentials
import com.kyc.mobile.domain.exception.KycMobileException
import com.kyc.mobile.domain.model.CustomerAction
import com.kyc.mobile.domain.usecase.AnalyticsRepository
import com.kyc.mobile.domain.usecase.CustomerTrackActionRepository
import com.kyc.mobile.domain.usecase.LocationRepository
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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val LOGIN_TAG = "Login"

class LoginViewModel(
    private val appContext: Context,
    private val loginRepository: LoginRepository,
    private val customerTrackActionRepository: CustomerTrackActionRepository,
    private val analyticsManager: AnalyticsRepository,
    private val locationRepository: LocationRepository
): ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState
        .onStart {
            loadData()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(6000),
            LoginState()
        )

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    fun loadData(){

        viewModelScope.launch(Dispatchers.IO){
            locationRepository.getLastLocation { location ->
               _loginState.update {
                   it.copy(currentLatitude = location.first, currentLongitude = location.second)
               }
            }
        }
    }

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
        _loginState.update{
            it.copy(state = DisplayState.Loading)
        }
        val firebaseParamsEvent = mutableMapOf<String,String>()
        firebaseParamsEvent["btn"] = "Login"
        viewModelScope.launch(Dispatchers.IO) {

            try{
                val credentials = UserCredentials(_loginState.value.username.value,
                    _loginState.value.password.value)

                Log.i(LOGIN_TAG, "Login user")
                loginRepository.login(credentials)
                firebaseParamsEvent["result"] = "Successful"

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
                firebaseParamsEvent["status"] = "Failure"
                analyticsManager.logException(screenName = "Login", ex)
                _loginState.update{
                    it.copy(state = DisplayState.Error(ex.errorData!!))
                }
            }
            finally {
                analyticsManager.logEvent(FirebaseAnalytics.Event.LOGIN, firebaseParamsEvent)
            }
        }
    }

    private suspend fun registerAction(sessionData: SessionData){

        val params = HashMap<String,String>()

        params["device"]= GeneralUtil.getDeviceId(appContext)
        params["ip"] = GeneralUtil.getLocalIpAddress(appContext)
        params["longitude"] = _loginState.value.currentLongitude.toString()
        params["latitude"] = _loginState.value.currentLatitude.toString()
        params["category"] = "Auth"
        params["event"] = "Login"

        val action = CustomerAction(customerNumber = sessionData.owner,
            trackId = TrackIdEnum.LOGIN.id.toString(), params )

        customerTrackActionRepository.registerAction(action)
    }
}