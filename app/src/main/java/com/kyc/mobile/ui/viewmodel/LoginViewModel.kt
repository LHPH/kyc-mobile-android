package com.kyc.mobile.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyc.mobile.domain.exception.KycMobileException
import com.kyc.mobile.domain.model.UserCredentials
import com.kyc.mobile.domain.usecase.LoginRepository
import com.kyc.mobile.ui.screens.login.LoginState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository
): ViewModel() {

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _isLoginEnabled = MutableLiveData<Boolean>()
    val isLoginEnabled: LiveData<Boolean> = _isLoginEnabled

    private val _errorUsername = MutableLiveData<Boolean>()
    val errorUsername : LiveData<Boolean> = _errorUsername

    private val _errorPassword = MutableLiveData<Boolean>()
    val errorPassword : LiveData<Boolean> = _errorPassword

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    private val _showPassword = MutableLiveData<Boolean>();
    val showPassword: LiveData<Boolean> = _showPassword;

    fun onUsernameChanged(username: String){

        _username.value = username
        _errorUsername.value = !isValidUsername(username)

        _isLoginEnabled.value = _errorUsername.value == false && _errorPassword.value == false
    }

    fun onPasswordChanged(password: String){

        _password.value = password
        _errorPassword.value = !isValidPassword(password)

        _isLoginEnabled.value = _errorUsername.value == false && _errorPassword.value == false
    }

    private fun isValidUsername(username: String): Boolean{

        val pattern = Regex("^[a-zA-Z0-9_]{6,10}$")
        return pattern.matches(username)
    }

    private fun isValidPassword(password: String): Boolean{

        val pattern = Regex("^[a-zA-Z0-9_#\\.\\+\\*\\$]{8,15}\$")
        return pattern.matches(password);
    }

    fun login(){

        val username = _username.value
        val password = _password.value

        if(_isLoginEnabled.value == true && username!=null && password!=null){

            _loginState.value = LoginState.Loading
            viewModelScope.launch(Dispatchers.IO) {

                try{
                    var credentials = UserCredentials(username,password)
                    loginRepository.login(credentials)
                    _loginState.value = LoginState.Success
                }
                catch(ex: KycMobileException){
                    _loginState.value = LoginState.Error(ex.errorData!!)
                }
            }
        }
    }

    fun resetToIdleState(){
       _loginState.value = LoginState.Idle
    }

    fun showPasswordOnScreen(showPassword: Boolean){
        _showPassword.value = !showPassword
    }
}