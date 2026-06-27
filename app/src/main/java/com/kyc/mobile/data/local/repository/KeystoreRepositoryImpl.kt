package com.kyc.mobile.data.local.repository

import com.kyc.mobile.data.util.SingletonHolder
import com.kyc.mobile.domain.security.AesCipher
import com.kyc.mobile.domain.usecase.KeystoreRepository
import java.security.KeyStore
import javax.crypto.SecretKey

class KeystoreRepositoryImpl private constructor(
    private val aesCipher: AesCipher
): KeystoreRepository {

    private val keyStore  = KeyStore.getInstance("AndroidKeyStore").apply {
        load { null }
    }

    override fun getSecretKey(entry: String): SecretKey {

        val existingKey = keyStore.getEntry(entry,null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: aesCipher.createKey(entry);
    }

    companion object: SingletonHolder<KeystoreRepositoryImpl, AesCipher>(::KeystoreRepositoryImpl)
}