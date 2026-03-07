package com.kyc.mobile.ui.screens.bills

sealed interface BillAction {

    data object OnLoad: BillAction
    data object OnDismissAlertError: BillAction
    data class OnClickDropdown(val state: Boolean): BillAction
    data class OnClickDropdownItem(val state: String): BillAction
}