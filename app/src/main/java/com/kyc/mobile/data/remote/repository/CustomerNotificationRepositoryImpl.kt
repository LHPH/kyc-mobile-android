package com.kyc.mobile.data.remote.repository

import android.util.Log
import com.kyc.mobile.data.remote.api.NotificationsApi
import com.kyc.mobile.data.remote.handlingApiResponse
import com.kyc.mobile.domain.model.CustomerNotification
import com.kyc.mobile.domain.usecase.CustomerNotificationRepository
import com.kyc.mobile.domain.util.DateUtil.Companion.parseLocalDateTimeToStringDateTime

class CustomerNotificationRepositoryImpl(
    private val notificationsApi: NotificationsApi
): CustomerNotificationRepository {

    override suspend fun getNotifications(): List<CustomerNotification> {

       val result = handlingApiResponse {
           notificationsApi.getNotifications()
       }
           .onSuccess {
               Log.i("CustomerNotificationRepositoryImpl","Successfully get notifications")
           }
           .onFailure { throwable ->
               Log.e("CustomerNotificationRepositoryImpl","Exception",throwable)
           }

        val responseData = result.getOrThrow()

        val notifications = responseData.data?: emptyList()

        return notifications.map {
            CustomerNotification(
                message = it.message,
                event= it.event,
                date = parseLocalDateTimeToStringDateTime(it.date)
            )
        }
    }
}