package com.kyc.mobile.data.remote

import android.util.Log
import com.kyc.mobile.data.remote.api.NotificationsApi
import com.kyc.mobile.data.remote.dto.MessageData
import com.kyc.mobile.domain.exception.KycMobileException
import com.kyc.mobile.domain.model.CustomerNotification
import com.kyc.mobile.domain.usecase.CustomerNotificationRepository

class CustomerNotificationRepositoryImpl(
    private val notificationsApi: NotificationsApi
): CustomerNotificationRepository {

    override suspend fun getNotifications(): List<CustomerNotification> {

        try {
            val notifications: ArrayList<CustomerNotification> = ArrayList()

            notifications.add(CustomerNotification(message = "Welcome to KYC", event = "INFO", date = "2029-10-10"))
            notifications.add(CustomerNotification(message = "Welcome to KYC", event = "INFO", date = "2029-10-10"))
            notifications.add(CustomerNotification(message = "Welcome to KYC", event = "ERROR", date = "2029-10-10"))
            notifications.add(CustomerNotification(message = "Welcome to KYC", event = "WARN", date = "2029-10-10"))
            notifications.add(CustomerNotification(message = "Welcome to KYC", event = "ERROR", date = "2029-10-10"))
            notifications.add(CustomerNotification(message = "Welcome to KYC", event = "WARN", date = "2029-10-10"))
            notifications.add(CustomerNotification(message = "Welcome to KYC", event = "ERROR", date = "2029-10-10"))
            notifications.add(CustomerNotification(message = "Welcome to KYC", event = "WARN", date = "2029-10-10"))
            notifications.add(CustomerNotification(message = "Welcome to KYC", event = "ERROR", date = "2029-10-10"))
            notifications.add(CustomerNotification(message = "Welcome to KYC", event = "WARN", date = "2029-10-10"))
            notifications.add(CustomerNotification(message = "Welcome to KYC", event = "ERROR", date = "2029-10-10"))

            val randomNumber = (1..11).random()
            if(randomNumber>5){
                return notifications
            }
            throw KycMobileException(errorData = MessageData(time=""), exception = RuntimeException("test"))

        }
        catch(ex: KycMobileException){
            throw ex
        }
        catch(ex: Exception){

            Log.e("CustomerNotificationRepositoryImpl","Exception",ex)
            var errorData = MessageData(message = "Unexpected error fetching notifications",time = "")
            throw KycMobileException(errorData, exception = ex)
        }
    }
}