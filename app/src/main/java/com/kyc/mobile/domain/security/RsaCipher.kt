package com.kyc.mobile.domain.security

import android.security.keystore.KeyProperties
import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.MGF1ParameterSpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.OAEPParameterSpec
import javax.crypto.spec.PSource

class RsaCipher {
    companion object {

        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_RSA
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_ECB
        private const val PADDING = "OAEPWithSHA-256AndMGF1Padding"
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
        private const val RSA_BIT_LENGTH = 2048

        private val oaepParameterSpec = OAEPParameterSpec(
            "SHA-256",
            "MGF1",
            MGF1ParameterSpec("SHA-256"), // Explicitly set MGF1 to SHA-256
            PSource.PSpecified.DEFAULT
        )
    }

    fun encrypt(plainText: String, key: PublicKey): String{

        val cipher = Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.ENCRYPT_MODE,key,oaepParameterSpec)
        }

        val encryptedText: ByteArray = cipher.doFinal(plainText.toByteArray(StandardCharsets.UTF_8))
        return Base64.encodeToString(encryptedText,Base64.NO_WRAP)
    }

    fun decrypt(encryptedText: String, key: PrivateKey): String{

        val cipher = Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.DECRYPT_MODE,key,oaepParameterSpec)
        }

        val bytesEncryptedText = Base64.decode(encryptedText, Base64.NO_WRAP)

        val plainText: ByteArray = cipher.doFinal(bytesEncryptedText)
        return String(plainText, StandardCharsets.UTF_8)
    }

    fun transformBase64BinaryKeyToPublicKey(value: String): PublicKey{

        val bytesPublicKey = Base64.decode(value,Base64.NO_WRAP)
        val spec = X509EncodedKeySpec(bytesPublicKey)
        val keyFactory = KeyFactory.getInstance(ALGORITHM)
        return keyFactory.generatePublic(spec)
    }

    fun matchesPublicKeys(first: PublicKey?, second: PublicKey?): Boolean{

        if(first == null || second == null){
            return false
        }

        return first.encoded.contentEquals(second.encoded)
    }
}