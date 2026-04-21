package com.kyc.mobile.ui.screens.permissions

import android.app.Activity
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kyc.mobile.R
import com.kyc.mobile.ui.shared.DisplayState
import com.kyc.mobile.ui.viewmodel.PermissionsViewModel

@Composable
fun PermissionsScreen(
    permissionsViewModel: PermissionsViewModel,
    onGranted: ()-> Unit = {}
){

    val context = LocalContext.current
    val permissionsState by permissionsViewModel.permissionState.collectAsStateWithLifecycle()

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        val granted = permissionsViewModel.checkPermissionsResult(results)
        if(granted){
            permissionsViewModel.onAction(PermissionAction.OnGrantedPermissions)
        }
        else{

            val rationalePermissions = results.keys.filter {
                ActivityCompat.shouldShowRequestPermissionRationale((context as Activity), it)
            }

            if(rationalePermissions.isNotEmpty()){
                permissionsViewModel.onAction(PermissionAction.OnShowRationaleDialog(true))
            }
            else{
                permissionsViewModel.onAction(PermissionAction.OnShowRationaleDialog(false))
            }
        }
    }

    LaunchedEffect(Unit) {
        val permissions = permissionsState.permissions
            .filter { it.required && !it.granted }
            .map { it.permission }
        launcher.launch(permissions.toTypedArray())
    }

    PermissionScreenView(
        permissionsState,
        permissionsViewModel::onAction,
        onGranted,
        {
            val intent = Intent(Settings.ACTION_SETTINGS)
            context.startActivity(intent)
        },
        {
            val permissions = permissionsState.permissions
                .filter { it.required && !it.granted }
                .map { it.permission }
            launcher.launch(permissions.toTypedArray())
        }
    )
}


@Composable
fun PermissionScreenView(
    permissionState: PermissionState,
    onAction: (PermissionAction) -> Unit = {},
    onGranted: ()-> Unit = {},
    onOpenSettings: ()-> Unit = {},
    onAttemptPermissions: () -> Unit = {}
){

    Box(modifier = Modifier
        .fillMaxSize()
        .paint(painter = painterResource(id = R.drawable.intro_kyc),
            contentScale = ContentScale.Crop)
    ){

        when(permissionState.state){

            is DisplayState.Loading,
               DisplayState.Idle ->{
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
            is DisplayState.Success->{
                onGranted()
            }
            is DisplayState.Exit->{
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = Color.Gray
                    ),
                    modifier = Modifier.align(Alignment.Center)
                        .fillMaxWidth()
                        .padding(25.dp)
                        .height(48.dp),
                    shape = MaterialTheme.shapes.medium,
                    onClick = {
                        onOpenSettings()
                    }) {
                        Text(
                            text = "Open System Settings",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
            }
            is DisplayState.Error -> {
                ShowRationaleDialog(
                    onDismiss = {
                        onAction(PermissionAction.OnShowRationaleDialog(false))
                    },
                    onConfirm = {
                        onAction(PermissionAction.OnConfirmRationaleDialog)
                        onAttemptPermissions()
                    }
                )
            }
        }

    }
}

@Composable
fun ShowRationaleDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
){

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Permissions")
        },
        text = {
            Text(text = "Permissions needed")
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview
@Composable
fun PermissionScreenPreview(){
    PermissionScreenView(PermissionState(state = DisplayState.Exit))
}