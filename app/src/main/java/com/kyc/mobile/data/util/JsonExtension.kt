package com.kyc.mobile.data.util

import com.kyc.mobile.data.remote.dto.ResponseData
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

object JsonDefaults{
    val instance:Json = Json{
        ignoreUnknownKeys = true
        encodeDefaults = true
    } // Configure Json instance as needed
}

inline fun <reified T> Json.decodeToResponseData(json: String): ResponseData<T>{

    val dataSerializer: KSerializer<T> = serializer<T>()
    val wrapperSerializer = ResponseData.serializer(dataSerializer)
    return JsonDefaults.instance.decodeFromString(wrapperSerializer,json)
}

inline fun <reified T> Json.decodeToGeneric(json: String): T{
    val dataSerializer: KSerializer<T> = serializer<T>()
    return JsonDefaults.instance.decodeFromString(dataSerializer,json)
}