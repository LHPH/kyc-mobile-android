package com.kyc.mobile.domain.util

import android.content.Context
import android.net.ConnectivityManager
import android.provider.Settings
import java.net.Inet4Address
import java.text.DecimalFormat

class GeneralUtil {

    companion object{

        fun doubleValueToStringFormat(value: Double): String{

            val decimalFormat = DecimalFormat("¤#,##0")
            return decimalFormat.format(value)
        }

        fun getDeviceId(context: Context): String{
            return Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        }

        fun getLocalIpAddress(context: Context): String{

            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetwork
            val linkProperties= connectivityManager.getLinkProperties(activeNetwork);

            return linkProperties?.linkAddresses?.map { it.address }?.find { it is Inet4Address }?.hostAddress ?: ""
        }
    }
}