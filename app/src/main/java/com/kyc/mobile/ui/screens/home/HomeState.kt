package com.kyc.mobile.ui.screens.home

import com.kyc.mobile.domain.model.MessageData

sealed class HomeState(){

    object Idle: HomeState()
    object Loading: HomeState()
    object Exit: HomeState()
    data class Error(val messageData: MessageData): HomeState()
}
