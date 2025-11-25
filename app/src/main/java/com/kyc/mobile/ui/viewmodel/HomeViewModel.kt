package com.kyc.mobile.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyc.mobile.domain.exception.KycMobileException
import com.kyc.mobile.domain.usecase.DataStoreRepository
import com.kyc.mobile.domain.usecase.LoginRepository
import com.kyc.mobile.ui.screens.home.HomeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val loginRepository: LoginRepository,
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

    private val _homeState = MutableStateFlow<HomeState>(HomeState.Idle)
    val homeState: StateFlow<HomeState> = _homeState;

    private val _menuExpanded =  MutableLiveData<Boolean>()
    val menuExpanded: LiveData<Boolean> = _menuExpanded

    private val _nameCustomer = MutableStateFlow<String>("")
    val nameCustomer: StateFlow<String> = _nameCustomer

    init{
        getUserPreferences()
    }

    fun onClickMenu(value: Boolean){
        _menuExpanded.value = value
    }

    fun getUserPreferences(){

        viewModelScope.launch{

            val userPreferences = dataStoreRepository.getUserPreferencesFromDataStore()
            _nameCustomer.value = userPreferences.name
        }
    }

    fun closeSession(){

        viewModelScope.launch(Dispatchers.IO) {

            try{
                loginRepository.logout()
                Log.i("Home", "Logout")
                _homeState.update {
                    HomeState.Exit
                }
            }
            catch(ex: KycMobileException){
                Log.i("","")
                _homeState.value = HomeState.Error(ex.errorData!!)
            }
        }
    }

}