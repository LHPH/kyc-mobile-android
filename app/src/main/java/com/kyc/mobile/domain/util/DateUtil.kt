package com.kyc.mobile.domain.util

import android.util.Log
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

const val DATE_TIME_FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss[.SSSSSSS][.SSSSSS][.SSSS][.SSS'Z']"

class DateUtil {

    companion object{

        fun parseLocalDateTimeToStringFormat(value: String, currentFormat: String, newFormat: String): String{
            //2025-12-04T00:12:36.797Z

            val inputDateFormat = DateTimeFormatter.ofPattern(currentFormat)
            val outputDateFormat = DateTimeFormatter.ofPattern(newFormat)
            return parseLocalDateTimeToStringFormat(value,inputDateFormat,outputDateFormat)
        }

        fun parseLocalDateTimeToStringFormat(value: String, inputDateFormat: DateTimeFormatter, outputDateFormat: DateTimeFormatter): String{

            try{
                val localDateTime = LocalDateTime.parse(value,inputDateFormat)
                return localDateTime.format(outputDateFormat)
            }
            catch(ex: DateTimeParseException){

                Log.e("DateUtil","Exception $ex")
                return value
            }
        }

        fun parseLocalDateTimeToStringIsoDate(value: String, currentFormat: String): String{

            return parseLocalDateTimeToStringFormat(value, currentFormat, "yyyy-MM-dd")
        }

        fun parseLocalDateTimeToStringDateTime(value: String?): String{

            return value?.let { parseLocalDateTimeToStringFormat(it,
                DATE_TIME_FORMAT_ISO_8601, "yyyy-MM-dd, HH:mm:ss") } ?: ""
        }
    }
}