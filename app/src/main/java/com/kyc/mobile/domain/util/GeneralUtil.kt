package com.kyc.mobile.domain.util

import android.content.Context
import android.provider.Settings
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
    }
}