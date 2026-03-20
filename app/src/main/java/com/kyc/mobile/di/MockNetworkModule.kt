package com.kyc.mobile.di

import android.content.Context
import com.kyc.mobile.data.mock.api.MockAuthApi
import com.kyc.mobile.data.mock.api.MockCustomerApplicationApi
import com.kyc.mobile.data.mock.api.MockCustomerBillsApi
import com.kyc.mobile.data.mock.api.MockCustomerTrackActionApi
import com.kyc.mobile.data.mock.api.MockNotificationsApi
import com.kyc.mobile.data.mock.api.MockOffersApi
import com.kyc.mobile.data.remote.api.AuthApi
import com.kyc.mobile.data.remote.api.CustomerApplicationApi
import com.kyc.mobile.data.remote.api.CustomerBillsApi
import com.kyc.mobile.data.remote.api.CustomerTrackActionApi
import com.kyc.mobile.data.remote.api.NotificationsApi
import com.kyc.mobile.data.remote.api.OfferApi
import kotlinx.serialization.json.Json

class MockNetworkModuleImpl(
    private val context: Context
): NetworkModule {

    val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    } // Configure Json instance as needed

    override val authApi: AuthApi by lazy{
        MockAuthApi(context)
    }

    override val customerApplicationApi: CustomerApplicationApi by lazy {
        MockCustomerApplicationApi(context)
    }

    override val customerTrackActionApi: CustomerTrackActionApi by lazy {
        MockCustomerTrackActionApi(context)
    }

    override val notificationApi: NotificationsApi by lazy {
        MockNotificationsApi(context)
    }

    override val offerApi: OfferApi by lazy {
        MockOffersApi(context)
    }

    override val customerBillsApi: CustomerBillsApi by lazy {
        MockCustomerBillsApi(context)
    }
}