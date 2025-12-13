package com.kyc.mobile.ui.shared

import com.kyc.mobile.data.remote.dto.MessageData

sealed class DisplayState(){

    object Idle: DisplayState()
    object Success: DisplayState()
    object Loading: DisplayState()
    object Exit: DisplayState()
    data class Error(val messageData: MessageData): DisplayState()
}