package com.kyc.mobile.data.mock.api

import android.content.Context
import com.kyc.mobile.data.remote.api.OfferApi
import com.kyc.mobile.data.remote.dto.OfferDataResp
import com.kyc.mobile.data.remote.dto.ResponseData
import retrofit2.Response

class MockOffersApi(
    private val context: Context
): OfferApi{

    override suspend fun getOffers(customerNumber: Long): Response<ResponseData<List<OfferDataResp>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getDetailOffer(
        customerNumber: Long,
        idOffer: Long
    ): Response<ResponseData<OfferDataResp>> {
        TODO("Not yet implemented")
    }

}