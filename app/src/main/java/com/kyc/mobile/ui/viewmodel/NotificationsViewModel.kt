package com.kyc.mobile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyc.mobile.domain.model.CustomerNotification
import com.kyc.mobile.ui.screens.notifications.NotificationsState
import com.kyc.mobile.ui.shared.DisplayState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotificationsViewModel(

): ViewModel(){

    private val _notificationsState = MutableStateFlow<NotificationsState>(NotificationsState())

    val notificationsState: StateFlow<NotificationsState> = _notificationsState
        .onStart {
            loadData()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 6000),
            NotificationsState()
        )

    private fun loadData(){

        viewModelScope.launch(Dispatchers.IO){

           // val userPreferences = dataStoreRepository.getUserPreferencesFromDataStore()
            //val services = customerApplicationRepository.getCustomerContractServices()

            delay(2000)
            val notifications: ArrayList<CustomerNotification> = ArrayList()

            notifications.add(CustomerNotification(message = "Welcome to KYC", event = "INFO", date = "2029-10-10"))
            notifications.add(CustomerNotification(message = "Welcome to KYC", event = "INFO", date = "2029-10-10"))
            notifications.add(CustomerNotification(message = "Welcome to KYC", event = "ERROR", date = "2029-10-10"))
            notifications.add(CustomerNotification(message = "Welcome to KYC", event = "WARN", date = "2029-10-10"))
            notifications.add(CustomerNotification(message = "Welcome to KYC", event = "ERROR", date = "2029-10-10"))

            _notificationsState.update{
                it.copy(
                    notifications = notifications,
                    state = DisplayState.Success
                )
            }
        }
    }
}