package com.kyc.mobile.ui.screens.intro


sealed interface IntroScreenAction{

    data class OnClickButton(val action: ()-> Unit): IntroScreenAction
    data object ResetStateToIdle: IntroScreenAction
}
