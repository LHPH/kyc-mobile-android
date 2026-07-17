package com.kyc.mobile.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyc.mobile.domain.exception.KycMobileException
import com.kyc.mobile.domain.model.CustomerNotification
import com.kyc.mobile.domain.usecase.CustomerNotificationRepository
import com.kyc.mobile.ui.screens.notifications.NotificationAction
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

const val NOTIFICATION_TAG = "NOTIFICATIONS"

class NotificationsViewModel(
    private val customerNotificationRepository: CustomerNotificationRepository
): ViewModel(){

    private val _notificationsState = MutableStateFlow(NotificationsState())

    val notificationsState: StateFlow<NotificationsState> = _notificationsState
        .onStart {
            onAction(NotificationAction.OnLoad)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 6000),
            NotificationsState()
        )

    private fun loadData(){

        viewModelScope.launch(Dispatchers.IO){

            try {
                val notifications: List<CustomerNotification> = customerNotificationRepository.getNotifications()

                _notificationsState.update{
                    it.copy(
                        notifications = notifications,
                        state = DisplayState.Success
                    )
                }
            }
            catch(ex: KycMobileException){

                Log.e(NOTIFICATION_TAG, "Error in Notifications",ex)
                _notificationsState.update{

                    val attempts = it.attempts.plus(1)
                    it.copy(
                        attempts = attempts,
                        state = DisplayState.Error(
                            messageData = ex.errorData!!
                        )
                    )
                }
            }
        }
    }

    fun onAction(action: NotificationAction){

        when(action){
            NotificationAction.OnLoad->{
                loadData()
            }
            NotificationAction.OnDismissAlertError->{
                _notificationsState.update {
                    it.copy(
                        state = DisplayState.Loading
                    )
                }
                loadData()
            }
            is NotificationAction.OnExitFatalError ->{
                action.errorAction()
            }
        }
    }
}