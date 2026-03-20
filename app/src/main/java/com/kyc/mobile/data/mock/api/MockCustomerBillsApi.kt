package com.kyc.mobile.data.mock.api

import android.content.Context
import com.kyc.mobile.data.remote.api.CustomerBillsApi
import com.kyc.mobile.data.remote.dto.CustomerBillsResp
import com.kyc.mobile.data.remote.dto.ResponseData
import retrofit2.Response

class MockCustomerBillsApi(
    private val context: Context
): CustomerBillsApi {
    override suspend fun getCustomerBills(): Response<ResponseData<List<CustomerBillsResp>>> {
        TODO("Not yet implemented")
    }
}