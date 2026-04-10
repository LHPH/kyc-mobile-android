package com.kyc.mobile.ui.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.kyc.mobile.domain.util.HomeMenuItemEnum


@Composable
fun AppBarScrollContent(
    menuExpanded: Boolean = false,
    onAction: (action: HomeAction) -> Unit ={},
) {

    IconButton(onClick = {
        onAction(HomeAction.OnClickDropdown(true))
    }) {
        Icon(Icons.Default.MoreVert, contentDescription = "More options")
    }
    DropdownMenu(
        expanded = menuExpanded,
        onDismissRequest = {
            onAction(HomeAction.OnClickDropdown(false))
        }
    ) {
        DropdownMenuItem(
            leadingIcon = {
                Icon(Icons.Default.Close, contentDescription = "Close Session")
            },
            text = {
                Text("Close Session")
            },
            onClick = {
               onAction(HomeAction.OnClickDropdownItem(HomeMenuItemEnum.CLOSE_SESSION))
            }
        )
    }
}