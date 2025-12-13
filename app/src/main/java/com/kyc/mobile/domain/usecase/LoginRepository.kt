package com.kyc.mobile.domain.usecase

import com.kyc.mobile.data.remote.dto.SessionData
import com.kyc.mobile.data.remote.dto.UserCredentials

interface LoginRepository {

    suspend fun login(credentials: UserCredentials)

    suspend fun logout()

    suspend fun sessionChecking(): SessionData
}