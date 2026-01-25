package com.kyc.mobile.domain.util

import android.util.Log
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

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
    }
}