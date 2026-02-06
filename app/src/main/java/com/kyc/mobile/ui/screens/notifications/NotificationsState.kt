package com.kyc.mobile.ui.screens.notifications

import com.kyc.mobile.domain.model.CustomerNotification
import com.kyc.mobile.ui.shared.DisplayState

data class NotificationsState(
    val notifications: List<CustomerNotification> = emptyList(),
    val state: DisplayState = DisplayState.Loading
)