package com.kyc.mobile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyc.mobile.ui.screens.notifications.NotificationsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

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

    }
}