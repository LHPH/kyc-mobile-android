package com.kyc.mobile.domain.usecase

import com.kyc.mobile.domain.model.CustomerContractService

interface CustomerApplicationRepository {

    suspend fun getCustomerContractServices(): List<CustomerContractService>
}