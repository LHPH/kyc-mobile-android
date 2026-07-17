package com.kyc.mobile.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyc.mobile.domain.exception.KycMobileException
import com.kyc.mobile.domain.model.CustomerBill
import com.kyc.mobile.domain.usecase.CustomerBillRepository
import com.kyc.mobile.ui.screens.bills.BillAction
import com.kyc.mobile.ui.screens.bills.BillsState
import com.kyc.mobile.ui.shared.DisplayState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val BILLS_TAG = "BILLS"

class BillViewModel(
    private val customerBillsRepository: CustomerBillRepository
): ViewModel(){

    private val _billState = MutableStateFlow(BillsState())
    val billState: StateFlow<BillsState> = _billState
        .onStart {
            loadData()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 6000),
            BillsState()
        )

    private fun loadData(){

        viewModelScope.launch(Dispatchers.IO){

            try {
                val customerBills = customerBillsRepository.getCustomerBills()

                _billState.update {
                    it.copy(bills =  customerBills, displayedBills = customerBills, state = DisplayState.Success)
                }
            }
            catch(ex: KycMobileException){

                Log.e(BILLS_TAG, "Error in Bills",ex)

                _billState.update{
                    val attempts = it.attempts.plus(1)
                    it.copy(
                        attempts = attempts,
                        state = DisplayState.Error(ex.errorData!!)
                    )
                }
            }
        }
    }

    fun onAction(action: BillAction){

        when(action){
            is BillAction.OnClickDropdown->{
                _billState.update {
                    it.copy(expandedDropdown = action.state)
                }
            }
            is BillAction.OnClickDropdownItem->{
                _billState.update {
                    it.copy(
                        displayBillState = action.state,
                        displayedBills = filterDisplayedBills(it.bills,action.state),
                        expandedDropdown = false
                    )
                }
            }
            is BillAction.OnDismissAlertError->{
                _billState.update {
                    it.copy(
                        state = DisplayState.Loading
                    )
                }
                loadData()
            }
            is BillAction.OnLoad->{
                loadData()
            }
            is BillAction.OnExitFatalError ->{
                action.errorAction()
            }
        }
    }

    private fun filterDisplayedBills(bills: List<CustomerBill>, state: String): List<CustomerBill>{

        if(state == ""){
            return bills
        }
        return bills.filter { state.equals(it.status,true)}
    }
}