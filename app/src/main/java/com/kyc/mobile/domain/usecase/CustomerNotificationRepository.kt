package com.kyc.mobile.domain.usecase

import com.kyc.mobile.domain.model.CustomerNotification

interface CustomerNotificationRepository {

    suspend fun getNotifications(): List<CustomerNotification>
}