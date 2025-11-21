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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.kyc.mobile.ui.viewmodel.HomeViewModel


@Composable
fun AppBarScrollContent(
    viewModel: HomeViewModel
) {

    //var menuExpanded by remember { mutableStateOf(false) }
    val menuExpanded: Boolean by viewModel.menuExpanded.observeAsState(false)

    IconButton(onClick = {
        viewModel.onClickMenu(true)
    }) {
        Icon(Icons.Default.MoreVert, contentDescription = "More options")
    }
    DropdownMenu(
        expanded = menuExpanded,
        onDismissRequest = { viewModel.onClickMenu(false) }
    ) {
        DropdownMenuItem(
            leadingIcon = {
                Icon(Icons.Default.Close, contentDescription = "Close Session")
            },
            text = {
                Text("Close Session")
            },
            onClick = {
                // Handle Option 1 click
                viewModel.closeSession()
                viewModel.onClickMenu(false)
            }
        )
    }
}