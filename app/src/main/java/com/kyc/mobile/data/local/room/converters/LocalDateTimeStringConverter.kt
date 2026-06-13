package com.kyc.mobile.data.local.room.converters

import android.util.Log
import androidx.room3.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object LocalDateTimeStringConverter {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    @JvmStatic
    fun fromString(value: String?): LocalDateTime? {

        return value?.let {
            try {
                LocalDateTime.parse(it, formatter)
            } catch (e: Exception) {
                Log.e("Unexpected","Could not parse date",e)
                return null
            }
        }
    }

    @TypeConverter
    @JvmStatic
    fun toString(dateTime: LocalDateTime?): String? {
        return dateTime?.format(formatter)
    }
}
