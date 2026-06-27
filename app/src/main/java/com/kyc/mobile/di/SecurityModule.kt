package com.kyc.mobile.di

import android.content.Context
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.RegistryConfiguration
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.aead.AesGcmKeyManager
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import com.kyc.mobile.data.local.repository.KeystoreRepositoryImpl
import com.kyc.mobile.domain.security.AesCipher
import com.kyc.mobile.domain.security.RsaCipher
import com.kyc.mobile.domain.usecase.KeystoreRepository

interface SecurityModule{
    val aesCipher: AesCipher
    val rsaCipher: RsaCipher
    val keysetHandle: KeysetHandle
    val aead: Aead
    val keystoreRepository: KeystoreRepository
}

class SecurityModuleImpl(
    private val context: Context
): SecurityModule{

    companion object{
        private const val KEYSET_NAME = "kyc_datastore_encrypted_keyset"
        private const val PREFERENCE_FILE_NAME = "kyc_tink_keyset_preference"
        private const val MASTER_KEY_URI = "android-keystore://kyc_datastore_master_key"
    }

    override val aesCipher by lazy{
        AesCipher()
    }

    override val rsaCipher by lazy {
        RsaCipher()
    }

    override val keysetHandle: KeysetHandle by lazy{
        AndroidKeysetManager.Builder()
            .withSharedPref(context,KEYSET_NAME,PREFERENCE_FILE_NAME)
            .withKeyTemplate(AesGcmKeyManager.aes256GcmTemplate())
            .withMasterKeyUri(MASTER_KEY_URI)
            .build()
            .keysetHandle
    }

    override val aead: Aead by lazy{
        AeadConfig.register()
        keysetHandle.getPrimitive(
            RegistryConfiguration.get(),
            Aead::class.java
        )
    }

    override val keystoreRepository: KeystoreRepository by lazy{
        KeystoreRepositoryImpl.getInstance(aesCipher)
    }
}