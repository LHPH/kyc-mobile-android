package com.kyc.mobile.data.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.RawRes
import androidx.core.content.ContextCompat
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

fun Context.hasPermission(permission: String): Boolean = ContextCompat.checkSelfPermission(this,permission) == PackageManager.PERMISSION_GRANTED

fun Context.hasPermissions(listPermissions: List<String>): Boolean{

    return listPermissions
        .map { hasPermission(it) }
        .all { it }
}