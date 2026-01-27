package com.kyc.mobile.domain.usecase

import com.kyc.mobile.domain.model.CustomerAction

interface CustomerTrackActionRepository {

    suspend fun registerAction(action: CustomerAction)
}