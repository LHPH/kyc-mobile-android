package com.kyc.mobile.data.remote

import android.util.Log
import com.kyc.mobile.data.remote.api.CustomerBillsApi
import com.kyc.mobile.data.remote.dto.MessageData
import com.kyc.mobile.domain.exception.KycMobileException
import com.kyc.mobile.domain.model.CustomerBill
import com.kyc.mobile.domain.usecase.CustomerBillRepository
import kotlinx.coroutines.delay

class CustomerBillRepositoryImpl(
    private val customerBillsApi: CustomerBillsApi
): CustomerBillRepository {

    override suspend fun getCustomerBills(): List<CustomerBill> {

        try{

            delay(2000)
            var customerBills = ArrayList<CustomerBill>()
            val customerBill = CustomerBill(
                id = 1, taxes = 10.2, subtotal = 20.0,total = 300.0, settled = false,
                status = "PAID", issueDate = "", billingStartDate = "", billingFinishDate = "",
                paymentDueDate =  "", settlementDate = ""
            )
            customerBills.add(customerBill.copy(status = "VALID"))
            customerBills.add(customerBill)
            customerBills.add(customerBill)
            customerBills.add(customerBill.copy(status = "EXPIRED"))
            customerBills.add(customerBill)
            return customerBills
        }
        catch(ex: KycMobileException){
            throw ex
        }
        catch (ex: Exception) {

            Log.e("CustomerBillRepositoryImpl","Exception",ex)
            var errorData = MessageData(message = "Unexpected error fetching notifications",time = "")
            throw KycMobileException(errorData, exception = ex)
        }
    }
}