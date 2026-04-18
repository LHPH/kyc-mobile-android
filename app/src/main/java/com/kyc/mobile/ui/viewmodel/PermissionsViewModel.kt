package com.kyc.mobile.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyc.mobile.data.remote.dto.MessageData
import com.kyc.mobile.data.util.hasPermission
import com.kyc.mobile.ui.screens.permissions.PermissionAction
import com.kyc.mobile.ui.screens.permissions.PermissionState
import com.kyc.mobile.ui.shared.DisplayState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class PermissionsViewModel(
    private val appContext: Context
) : ViewModel(){

    private val _permissionState = MutableStateFlow(PermissionState())
    val permissionState: StateFlow<PermissionState> = _permissionState
        .onStart {
            loadData()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(6000),
            PermissionState()
        )

    fun loadData(){

        val permissions = _permissionState.value.permissions

        val updatedPermissions =  permissions.map {
           it.copy(granted = appContext.hasPermission(it.permission))
        }

        val grantedRequiredPermissions = updatedPermissions.filter { it.required }.all { it.granted }

        val initialState = if(grantedRequiredPermissions){
            DisplayState.Success
        }
        else{
            DisplayState.Loading
        }

        _permissionState.update {
            it.copy(
                granted = grantedRequiredPermissions,
                permissions = updatedPermissions,
                state = initialState
            )
        }
    }

    fun onAction(action: PermissionAction){

        when(action){
            is PermissionAction.OnGrantedPermissions -> {
                _permissionState.update {
                    it.copy(granted = true, showRationaleDialog = false, state = DisplayState.Success)
                }
            }
            is PermissionAction.OnShowRationaleDialog ->{

                val state = if(!action.value) {
                    DisplayState.Exit
                } else DisplayState.Error()

                _permissionState.update {
                    it.copy(showRationaleDialog = action.value, state = state)
                }
            }
            is PermissionAction.OnConfirmRationaleDialog ->{
                _permissionState.update {
                    it.copy(fireLauncher = it.fireLauncher+1, state = DisplayState.Loading)
                }
            }
            is PermissionAction.OnRejectRationaleDialog ->{

            }
        }
    }

    fun checkRationalePermissions(rationalePermissions: List<String>){



    }
}