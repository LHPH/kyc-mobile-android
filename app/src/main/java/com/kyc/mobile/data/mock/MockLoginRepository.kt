package com.kyc.mobile.data.mock

import com.kyc.mobile.data.remote.dto.SessionData
import com.kyc.mobile.data.remote.dto.UserCredentials
import com.kyc.mobile.domain.usecase.LoginRepository

class MockLoginRepository(): LoginRepository{
    override suspend fun login(credentials: UserCredentials) {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }

    override suspend fun sessionChecking(): SessionData {
        TODO("Not yet implemented")
    }

}