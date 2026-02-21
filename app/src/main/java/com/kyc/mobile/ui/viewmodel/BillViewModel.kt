package com.kyc.mobile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyc.mobile.ui.screens.bills.BillAction
import com.kyc.mobile.ui.screens.bills.BillsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

const val BILLS_TAG = "BILLS"

class BillViewModel(

): ViewModel(){

    val _billState = MutableStateFlow(BillsState())
    val billState: StateFlow<BillsState> = _billState
        .onStart {

        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 6000),
            BillsState()
        )

    private fun loadData(){

    }

    fun onAction(action: BillAction){

    }
}