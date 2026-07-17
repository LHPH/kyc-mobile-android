package com.kyc.mobile.ui.screens.bills

sealed interface BillAction {

    data object OnLoad: BillAction
    data object OnDismissAlertError: BillAction
    data class OnClickDropdown(val state: Boolean): BillAction
    data class OnClickDropdownItem(val state: String): BillAction
    data class OnExitFatalError(val errorAction: ()-> Unit): BillAction

    companion object{

        fun fromAttempts(attempts: Int, errorAction: () -> Unit): BillAction{
            return if(attempts<3) OnDismissAlertError else OnExitFatalError(errorAction)
        }
    }
}