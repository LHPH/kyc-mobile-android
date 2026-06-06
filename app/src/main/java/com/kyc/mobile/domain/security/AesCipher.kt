package com.kyc.mobile.domain.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec


class AesCipher{

    companion object{

        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_GCM
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_NONE
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
        private const val AES_KEY_LENGTH: Int = 256
        private const val TAG_LENGTH_BIT: Int = 128
        private const val IV_LENGTH_BYTE: Int = 16
    }

    fun createKey(): SecretKey{
        return KeyGenerator.getInstance(ALGORITHM, "AndroidKeyStore").apply {
            init(
                KeyGenParameterSpec.Builder("kyc-mobile-aes-key",
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setKeySize(AES_KEY_LENGTH)
                    .setBlockModes(BLOCK_MODE)
                    .setUserAuthenticationRequired(false)
                    .setRandomizedEncryptionRequired(true)
                    .build()
            )
        }.generateKey()
    }

    fun createEphemeralKey(): SecretKey{
        return KeyGenerator.getInstance(ALGORITHM).apply {
            init(AES_KEY_LENGTH)
        }.generateKey()
    }

    fun getRandomNonce(size: Int): ByteArray{

        val nonce = ByteArray(size)
        val secureRandom = SecureRandom()
        secureRandom.nextBytes(nonce)
        return nonce
    }

    fun encrypt(plainText: String, key: SecretKey): String{

        val iv: ByteArray = getRandomNonce(IV_LENGTH_BYTE)
        val bytesPlainText = plainText.toByteArray(StandardCharsets.UTF_8)

        val bytesEncryptedText =  encryptWithIv(bytesPlainText,key,iv)
        return Base64.encodeToString(bytesEncryptedText, Base64.NO_WRAP)
    }

    fun encrypt(plainText: ByteArray, key: SecretKey, iv: ByteArray): ByteArray{

        val cipher = Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.ENCRYPT_MODE,key, GCMParameterSpec(TAG_LENGTH_BIT,iv))
        }
        return cipher.doFinal(plainText)
    }

    fun encryptWithIv(plainText: ByteArray, key: SecretKey, iv: ByteArray): ByteArray{

        val encryptedText = encrypt(plainText, key, iv)

        return ByteBuffer.allocate(iv.size + encryptedText.size)
            .put(iv)
            .put(encryptedText)
            .array()
    }

    fun decrypt(encryptedText: String, key: SecretKey): String{

        val bytesEncryptedText = Base64.decode(encryptedText,Base64.NO_WRAP)
        return String(decryptWithIv(bytesEncryptedText,key))
    }

    fun decrypt(encryptedText: ByteArray, key: SecretKey, iv: ByteArray): ByteArray{

        val cipher = Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.DECRYPT_MODE,key, GCMParameterSpec(TAG_LENGTH_BIT,iv))
        }
        return cipher.doFinal(encryptedText)
    }

    fun decryptWithIv(encryptedText: ByteArray, key: SecretKey): ByteArray{

        val byteBuffer = ByteBuffer.wrap(encryptedText)
        val iv = ByteArray(IV_LENGTH_BYTE)
        byteBuffer.get(iv);

        val encryptedContent = ByteArray(byteBuffer.remaining())
        byteBuffer.get(encryptedContent);

        return decrypt(encryptedContent, key, iv);
    }

}

