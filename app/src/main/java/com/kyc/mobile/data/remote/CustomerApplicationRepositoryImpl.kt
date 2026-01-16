package com.kyc.mobile.data.remote

import android.util.Log
import com.kyc.mobile.data.remote.api.CustomerApplicationApi
import com.kyc.mobile.data.remote.dto.ContractedServiceResp
import com.kyc.mobile.data.remote.dto.ResponseData
import com.kyc.mobile.data.util.ApiUtil
import com.kyc.mobile.data.util.processResponseData
import com.kyc.mobile.domain.model.CustomerContractService
import com.kyc.mobile.domain.usecase.CustomerApplicationRepository
import java.util.stream.Collectors

class CustomerApplicationRepositoryImpl(
    private val customerApplicationApi: CustomerApplicationApi
): CustomerApplicationRepository {


    override suspend fun getCustomerContractServices(): List<CustomerContractService> {

        try{
            val response = customerApplicationApi.contractedServices()
            val result: ResponseData<List<ContractedServiceResp>> = response.processResponseData()
            val error = ApiUtil.checkIfError(result)

            val listServices = result.data?: emptyList()

            return listServices.stream()
                .map {
                    CustomerContractService(
                        folio = it.folio,
                        service = it.service,
                        cost= it.cost,
                        active = it.active,
                        acceptPromotions = it.promotions.acceptPromotions,
                        acceptPromotionsEmail = it.promotions.acceptPromotionsEmail,
                        acceptPromotionsCellPhone = it.promotions.acceptPromotionsCellPhone,
                        creationDate = it.modificationDate ?: ""
                    )
                }.collect(Collectors.toList())

        }
        catch(ex: Exception){
            Log.e("CustomerApplication","Exception",ex)
            return emptyList()
        }
    }
}