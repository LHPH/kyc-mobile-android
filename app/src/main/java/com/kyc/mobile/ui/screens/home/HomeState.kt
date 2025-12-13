package com.kyc.mobile.ui.screens.home

import com.kyc.mobile.domain.model.CustomerContractService
import com.kyc.mobile.ui.shared.DisplayState

data class HomeState(
    val customerName: String = "",
    val services: List<CustomerContractService> = emptyList<CustomerContractService>(),
    val state: DisplayState = DisplayState.Idle
)
