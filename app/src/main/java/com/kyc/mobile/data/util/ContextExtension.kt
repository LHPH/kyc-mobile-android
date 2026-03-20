package com.kyc.mobile.data.util

import android.content.Context
import androidx.annotation.RawRes
import okio.IOException

fun Context.readRawResource(@RawRes resId: Int): String{

    try {
        return this.resources.openRawResource(resId)
            .use{ inputStream ->
                inputStream.bufferedReader()
                    .use { it.readText() }
        }
    }
    catch(ex: IOException){
        ex.printStackTrace()
        return ""
    }
}