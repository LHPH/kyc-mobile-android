package com.kyc.mobile.ui.screens.bills

import com.kyc.mobile.domain.model.CustomerBill
import com.kyc.mobile.ui.shared.DisplayState

data class BillsState(
    val bills: List<CustomerBill> = emptyList(),
    val state: DisplayState = DisplayState.Loading
)