package com.kyc.mobile.ui.screens.permissions

sealed interface PermissionAction {

    data class OnShowRationaleDialog(val value: Boolean): PermissionAction
    object OnGrantedPermissions: PermissionAction

    object OnConfirmRationaleDialog: PermissionAction
    object OnRejectRationaleDialog: PermissionAction
}