package com.kyc.mobile.ui.screens.home

import com.kyc.mobile.domain.util.HomeMenuItemEnum


sealed interface HomeAction {

    data object OnLoad: HomeAction
    data object OnCloseSession: HomeAction
    data class OnClickDropdown(val state: Boolean): HomeAction
    data class OnClickDropdownItem(val item: HomeMenuItemEnum): HomeAction
}