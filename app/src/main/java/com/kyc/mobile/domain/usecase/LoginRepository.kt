package com.kyc.mobile.domain.usecase

import com.kyc.mobile.domain.model.UserCredentials

interface LoginRepository {

    suspend fun login(credentials: UserCredentials)
}