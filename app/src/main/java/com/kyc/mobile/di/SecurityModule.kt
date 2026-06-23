package com.kyc.mobile.di

import com.kyc.mobile.domain.security.AesCipher
import com.kyc.mobile.domain.security.RsaCipher

interface SecurityModule{
    val aesCipher: AesCipher
    val rsaCipher: RsaCipher
}

class SecurityModuleImpl: SecurityModule{

    override val aesCipher by lazy{
        AesCipher()
    }

    override val rsaCipher by lazy {
        RsaCipher()
    }

}