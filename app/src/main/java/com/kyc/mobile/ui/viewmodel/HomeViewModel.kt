package com.kyc.mobile.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyc.mobile.domain.exception.KycMobileException
import com.kyc.mobile.domain.model.CustomerAction
import com.kyc.mobile.domain.usecase.CustomerApplicationRepository
import com.kyc.mobile.domain.usecase.CustomerTrackActionRepository
import com.kyc.mobile.domain.usecase.DataStoreRepository
import com.kyc.mobile.domain.usecase.LoginRepository
import com.kyc.mobile.domain.util.TrackIdEnum
import com.kyc.mobile.ui.screens.home.HomeState
import com.kyc.mobile.ui.shared.DisplayState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val loginRepository: LoginRepository,
    private val customerApplicationRepository: CustomerApplicationRepository,
    private val customerTrackActionRepository: CustomerTrackActionRepository,
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

    private val _homeState = MutableStateFlow<HomeState>(HomeState())
    val homeState: StateFlow<HomeState> = _homeState
        .onStart {
            loadData()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(6000),
            HomeState()
        )

    private val _menuExpanded =  MutableLiveData<Boolean>()
    val menuExpanded: LiveData<Boolean> = _menuExpanded

    fun onClickMenu(value: Boolean){
        _menuExpanded.value = value
    }

    fun loadData(){

        viewModelScope.launch(Dispatchers.IO){

            val userPreferences = dataStoreRepository.getUserPreferencesFromDataStore()
            val services = customerApplicationRepository.getCustomerContractServices()

            _homeState.update{
                it.copy(
                    customerName = userPreferences.name,
                    services = services,
                    state = DisplayState.Idle
                )
            }
        }
    }

    fun closeSession(){

        viewModelScope.launch(Dispatchers.IO) {

            try{

                val params = HashMap<String,String>()
                params["category"] = "Auth"
                params["event"] = "Logout"

                val userPreferences = dataStoreRepository.getUserPreferencesFromDataStore()
                val customerAction = CustomerAction(customerNumber = userPreferences.customerId,
                    trackId = TrackIdEnum.HOME.id.toString(),params)

                customerTrackActionRepository.registerAction(customerAction)

                loginRepository.logout()
                Log.i("Home", "Logout")
                _homeState.update {
                    it.copy(state = DisplayState.Exit)
                }
            }
            catch(ex: KycMobileException){
                Log.i("","")
                _homeState.update {
                    it.copy(state = DisplayState.Error(ex.errorData!!))
                }
            }
        }
    }

}