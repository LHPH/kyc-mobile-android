package com.kyc.mobile.ui.screens.notifications

sealed interface NotificationAction {

    data object OnLoad: NotificationAction
    data object OnDismissAlertError: NotificationAction
}