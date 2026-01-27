package com.kyc.mobile.data.remote

import android.util.Log
import com.kyc.mobile.data.remote.api.CustomerTrackActionApi
import com.kyc.mobile.data.remote.dto.CustomerTrackActionReq
import com.kyc.mobile.domain.model.CustomerAction
import com.kyc.mobile.domain.usecase.CustomerTrackActionRepository

class CustomerTrackActionRepositoryImpl(
    private val customerTrackActionApi: CustomerTrackActionApi
): CustomerTrackActionRepository {

    override suspend fun registerAction(action: CustomerAction) {

        try{

            val req = CustomerTrackActionReq(
                customerNumber = action.customerNumber,
                trackId = action.trackId,
                params = action.params)

            customerTrackActionApi.trackAction(req)
        }
        catch(ex: Exception){
            Log.e("CustomerTrackActionRepository","Exception",ex)
        }
    }
}