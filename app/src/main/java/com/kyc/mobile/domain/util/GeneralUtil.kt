package com.kyc.mobile.domain.util

import java.text.DecimalFormat

class GeneralUtil {

    companion object{

        fun doubleValueToStringFormat(value: Double): String{

            val decimalFormat = DecimalFormat("¤#,##0")
            return decimalFormat.format(value)
        }
    }
}