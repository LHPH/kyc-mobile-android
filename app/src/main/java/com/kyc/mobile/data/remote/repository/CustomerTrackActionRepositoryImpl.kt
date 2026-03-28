package com.kyc.mobile.data.remote.repository

import android.util.Log
import com.kyc.mobile.data.remote.api.CustomerTrackActionApi
import com.kyc.mobile.data.remote.dto.CustomerTrackActionReq
import com.kyc.mobile.data.remote.handlingApiResponse
import com.kyc.mobile.domain.model.CustomerAction
import com.kyc.mobile.domain.usecase.CustomerTrackActionRepository

class CustomerTrackActionRepositoryImpl(
    private val customerTrackActionApi: CustomerTrackActionApi
): CustomerTrackActionRepository {

    override suspend fun registerAction(action: CustomerAction) {

        val req = CustomerTrackActionReq(
            customerNumber = action.customerNumber,
            trackId = action.trackId,
            params = action.params)

        handlingApiResponse {
            customerTrackActionApi.trackAction(req)
        }
            .onSuccess {
                Log.i("CustomerTrackActionRepository","Successfully save action")
            }
            .onFailure { throwable ->
                Log.e("CustomerTrackActionRepository","Exception",throwable)
            }
    }
}