package com.kyc.mobile.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyc.mobile.domain.exception.KycMobileException
import com.kyc.mobile.domain.model.LocalProperty
import com.kyc.mobile.domain.usecase.PropertiesRepository
import com.kyc.mobile.domain.usecase.PublicRepository
import com.kyc.mobile.domain.usecase.RemoteConfigRepository
import com.kyc.mobile.domain.util.PropertyKeyEnum
import com.kyc.mobile.ui.screens.intro.IntroScreenAction
import com.kyc.mobile.ui.screens.intro.IntroScreenState
import com.kyc.mobile.ui.shared.DisplayState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val SPLASH_SCREEN_TEXT = "splash_screen_text"

const val INTRO_SCREEN_TAG = "INTRO_SCREEN"
class IntroScreenViewModel(
    private val remoteConfig: RemoteConfigRepository,
    private val propertyRepository: PropertiesRepository,
    private val publicRepository: PublicRepository
): ViewModel(){

    private val _splashScreenText = MutableStateFlow("")
    val splashScreenText: StateFlow<String> =_splashScreenText
        .onStart {
            loadText()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(6000),
            "")

    private val _introScreenState = MutableStateFlow(IntroScreenState())
    val introScreenState: StateFlow<IntroScreenState> = _introScreenState
        .onStart {
            loadData()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(6000),
            IntroScreenState()
        )

    fun loadText() {

        viewModelScope.launch {
            remoteConfig.fetchUpdate(SPLASH_SCREEN_TEXT).collect { updatedValue ->
                _splashScreenText.value = updatedValue
            }
        }
    }

    fun loadData(){

        viewModelScope.launch(Dispatchers.IO) {

            try{

                val existsPublicKey = propertyRepository.checkPropertyByKey(PropertyKeyEnum.KYC_GTW_PUBLIC_KEY.name)
                if(!existsPublicKey){
                    val publicKey = publicRepository.getPublicKey()
                    val publicKeyProperty = LocalProperty(
                        id = 0,
                        propertyName = PropertyKeyEnum.KYC_GTW_PUBLIC_KEY.name,
                        propertyValue =  publicKey
                    )
                    propertyRepository.insertProperty(publicKeyProperty)
                    Log.i(INTRO_SCREEN_TAG,"SAVED $publicKeyProperty")
                }
                _introScreenState.update {
                    it.copy(state = DisplayState.Success)
                }
            }
            catch(ex: KycMobileException){

                _introScreenState.update {
                    it.copy(state = DisplayState.Error(ex.errorData))
                }
            }
        }
    }

    fun onAction(introScreenAction: IntroScreenAction){

        when(introScreenAction){
            IntroScreenAction.ResetStateToIdle ->{

                _introScreenState.update{
                    it.copy(state = DisplayState.Loading)
                }
                loadData()
            }

            is IntroScreenAction.OnClickButton ->{
                introScreenAction.action()
            }
        }
    }

}