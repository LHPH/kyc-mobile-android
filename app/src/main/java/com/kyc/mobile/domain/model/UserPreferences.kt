package com.kyc.mobile.domain.model

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import java.util.Base64

@Serializable
data class UserPreferences(
    val token: String = "",
    val userId: Long = 0,
    val customerId: Long = 0,
    val role: String = "",
    val name: String = ""
)

object UserPreferencesSerializable: Serializer<UserPreferences>{

    override val defaultValue: UserPreferences
        get() = UserPreferences()

    override suspend fun readFrom(input: InputStream): UserPreferences {
        val bytes = withContext(Dispatchers.IO) {
            input.use {
                it.readBytes()
            }
        }
        val bytesDecoded = Base64.getDecoder().decode(bytes)
        //Decrypted
        val json = bytesDecoded.decodeToString()
        return Json.decodeFromString(json)
    }

    override suspend fun writeTo(
        data: UserPreferences,
        output: OutputStream
    ) {
        val json = Json.encodeToString(data)
        val bytes: ByteArray = json.toByteArray(Charsets.UTF_8)
        //Encrypt
        val strBase64 = Base64.getEncoder().encode(bytes)
        withContext(Dispatchers.IO) {
            output.use {
                it.write(strBase64)
            }
        }
    }

}
