package com.kyc.mobile.data.remote.repository

import android.util.Log
import com.kyc.mobile.data.remote.api.CustomerApplicationApi
import com.kyc.mobile.data.remote.dto.ContractedServiceResp
import com.kyc.mobile.data.remote.dto.ResponseData
import com.kyc.mobile.data.remote.handlingApiResponse
import com.kyc.mobile.domain.model.CustomerContractService
import com.kyc.mobile.domain.usecase.CustomerApplicationRepository
import com.kyc.mobile.domain.util.DATE_TIME_FORMAT_ISO_8601
import com.kyc.mobile.domain.util.DateUtil.Companion.parseLocalDateTimeToStringIsoDate

class CustomerApplicationRepositoryImpl(
    private val customerApplicationApi: CustomerApplicationApi
): CustomerApplicationRepository {


    override suspend fun getCustomerContractServices(): List<CustomerContractService> {

        val result: Result<ResponseData<List<ContractedServiceResp>>> = handlingApiResponse {
            customerApplicationApi.contractedServices()
        }
            .onSuccess {
                Log.i("CustomerApplication", "Successfully fetch contracted services")
            }
            .onFailure { throwable ->
                Log.e("CustomerApplication", "Exception", throwable)
            }

        val responseData = result.getOrDefault(ResponseData(data = emptyList()))
        val listServices = responseData.data?: emptyList()

        return listServices
            .map {
                CustomerContractService(
                    folio = it.folio,
                    service = it.service,
                    cost= it.cost,
                    active = it.active,
                    acceptPromotions = it.promotions.acceptPromotions,
                    acceptPromotionsEmail = it.promotions.acceptPromotionsEmail,
                    acceptPromotionsCellPhone = it.promotions.acceptPromotionsCellPhone,
                    creationDate = parseLocalDateTimeToStringIsoDate(it.creationDate,DATE_TIME_FORMAT_ISO_8601)
                )
            }
    }
}