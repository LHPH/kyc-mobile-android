package com.kyc.mobile.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class LoginViewModel: ViewModel() {

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _isLoginEnabled = MutableLiveData<Boolean>()
    val isLoginEnabled: LiveData<Boolean> = _isLoginEnabled

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun onLoginChanged(username: String, password: String){

        _username.value = username
        _password.value = password

        _isLoginEnabled.value = isValidUsername(username) && isValidPassword(password)
    }

    suspend fun onLoginSelected(){

        _isLoading.value = true
        delay(3000)
        _isLoading.value = false
    }

    private fun isValidUsername(username: String): Boolean{

        return true;
    }

    private fun isValidPassword(password: String): Boolean{

        return true
    }
}