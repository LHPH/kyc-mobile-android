package com.kyc.mobile.domain.model

import androidx.datastore.core.Serializer
import com.google.crypto.tink.Aead
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
    val name: String = "",
    val publicKeyTimestamp : Long = 0
)

class UserPreferencesSerializable(
    val aead: Aead
): Serializer<UserPreferences>{

    override val defaultValue: UserPreferences
        get() = UserPreferences()

    override suspend fun readFrom(input: InputStream): UserPreferences {
        val encryptedBytes = withContext(Dispatchers.IO) {
            input.use {
                it.readBytes()
            }
        }
        val bytes = aead.decrypt(encryptedBytes,null)
        val bytesDecoded = Base64.getDecoder().decode(bytes)
        val json = bytesDecoded.decodeToString()
        return Json.decodeFromString(json)
    }

    override suspend fun writeTo(
        data: UserPreferences,
        output: OutputStream
    ) {
        val json = Json.encodeToString(data)
        val bytes: ByteArray = json.encodeToByteArray()
        val strBase64 = Base64.getEncoder().encode(bytes)
        val encryptedBytes = aead.encrypt(strBase64, null)
        withContext(Dispatchers.IO) {
            output.use {
                it.write(encryptedBytes)
            }
        }
    }

}
