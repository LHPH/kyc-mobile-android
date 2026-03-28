package com.kyc.mobile.data.remote.repository

import android.util.Log
import com.kyc.mobile.data.remote.api.CustomerBillsApi
import com.kyc.mobile.data.remote.handlingApiResponse
import com.kyc.mobile.domain.model.CustomerBill
import com.kyc.mobile.domain.usecase.CustomerBillRepository
import com.kyc.mobile.domain.util.DateUtil.Companion.parseLocalDateTimeToStringDateTime

class CustomerBillRepositoryImpl(
    private val customerBillsApi: CustomerBillsApi
): CustomerBillRepository {

    override suspend fun getCustomerBills(): List<CustomerBill> {

        val result = handlingApiResponse {
            customerBillsApi.getCustomerBills()
        }
            .onSuccess {
                Log.i("CustomerNotificationRepositoryImpl","Successfully get bills")
            }
            .onFailure { throwable ->
                Log.e("CustomerBillRepository","Exception",throwable)
            }

        val responseData = result.getOrThrow()

        val bills = responseData.data?: emptyList()

        return bills.map {
            CustomerBill(
                id = it.id,
                taxes = it.taxes,
                subtotal = it.subtotal,
                total = it.total,
                settled = it.settled,
                status = it.status,
                issueDate = parseLocalDateTimeToStringDateTime(it.issueDate),
                billingStartDate = it.billingStartDate,
                billingFinishDate = it.billingFinishDate,
                paymentDueDate = it.paymentDueDate,
                settlementDate = parseLocalDateTimeToStringDateTime(it.settlementDate)
            )
        }
    }
}