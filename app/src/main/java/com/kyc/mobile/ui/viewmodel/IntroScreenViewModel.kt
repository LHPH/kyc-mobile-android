package com.kyc.mobile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyc.mobile.domain.usecase.RemoteConfigRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

const val SPLASH_SCREEN_TEXT = "splash_screen_text"

class IntroScreenViewModel(
    private val remoteConfig: RemoteConfigRepository
): ViewModel(){

    private val _splashScreenText = MutableStateFlow("")
    val splashScreenText: StateFlow<String> =_splashScreenText
        .onStart {
            loadData()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(6000),
            "")

    fun loadData(){
        remoteConfig.fetchAndActivate { success->
            if(success){
                _splashScreenText.update {
                    remoteConfig.getConfigStringValue(SPLASH_SCREEN_TEXT)
                }
            }
        }
    }
}