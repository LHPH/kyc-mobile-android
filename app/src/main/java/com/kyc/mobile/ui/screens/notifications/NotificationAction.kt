package com.kyc.mobile.ui.screens.notifications

sealed interface NotificationAction {

    data object OnLoad: NotificationAction
    data object OnDismissAlertError: NotificationAction

    data class OnExitFatalError(val errorAction: ()-> Unit): NotificationAction

    companion object{

        fun fromAttempts(attempts: Int, errorAction: () -> Unit): NotificationAction{
            return if(attempts<3) OnDismissAlertError else OnExitFatalError(errorAction)
        }
    }
}