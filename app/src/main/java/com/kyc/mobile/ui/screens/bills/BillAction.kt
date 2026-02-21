package com.kyc.mobile.ui.screens.bills

sealed interface BillAction {

    data object OnLoad: BillAction
    data object OnDismissAlertError: BillAction
}