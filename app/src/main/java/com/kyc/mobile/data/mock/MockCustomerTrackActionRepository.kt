package com.kyc.mobile.data.mock

import com.kyc.mobile.domain.model.CustomerAction
import com.kyc.mobile.domain.usecase.CustomerTrackActionRepository

class MockCustomerTrackActionRepository: CustomerTrackActionRepository {
    override suspend fun registerAction(action: CustomerAction) {
        TODO("Not yet implemented")
    }
}