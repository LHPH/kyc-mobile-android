package com.kyc.mobile.domain.usecase

import com.kyc.mobile.domain.model.CustomerBill

interface CustomerBillRepository {

    suspend fun getCustomerBills(): List<CustomerBill>
}