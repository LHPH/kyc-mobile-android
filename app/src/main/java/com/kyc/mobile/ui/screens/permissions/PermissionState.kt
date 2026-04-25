package com.kyc.mobile.ui.screens.permissions

import android.Manifest
import com.kyc.mobile.ui.shared.DisplayState

data class PermissionState(
    val granted: Boolean = false,
    val showRationaleDialog: Boolean = false,
    val state: DisplayState = DisplayState.Loading,
    val permissions: List<PermissionData> = listOf(
        PermissionData(Manifest.permission.INTERNET,true),
        PermissionData(Manifest.permission.ACCESS_NETWORK_STATE,true),
        PermissionData(Manifest.permission.ACCESS_COARSE_LOCATION,true),
        PermissionData(Manifest.permission.ACCESS_FINE_LOCATION,true),
        PermissionData(Manifest.permission.POST_NOTIFICATIONS,true)
    )
)

data class PermissionData(
    val permission : String,
    val required: Boolean,
    val granted: Boolean = false
)
