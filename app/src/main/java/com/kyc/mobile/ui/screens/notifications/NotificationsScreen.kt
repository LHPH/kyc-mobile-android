package com.kyc.mobile.ui.screens.notifications

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kyc.mobile.R
import com.kyc.mobile.ui.shared.DisplayState
import com.kyc.mobile.ui.shared.KycAlertDialog
import com.kyc.mobile.ui.theme.algerianFontFamily
import com.kyc.mobile.ui.viewmodel.NotificationsViewModel

@Composable
fun NotificationsScreen(
    notificationsViewModel: NotificationsViewModel,
    onClickBack: () -> Unit
){

    val notificationsState: NotificationsState by notificationsViewModel.notificationsState.collectAsStateWithLifecycle()
    NotificationView(notificationsState, onClickBack,notificationsViewModel::onAction)
}

@Composable
fun NotificationView(
    notificationState: NotificationsState,
    onClickBack: () -> Unit={},
    onAction: (action: NotificationAction) -> Unit = {}
){

    Box(
        modifier = Modifier.fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.intro_kyc),
                contentScale = ContentScale.Crop)
            .padding(top = 50.dp)

    ){
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .padding(start = 30.dp)
                .clickable(enabled = true, onClick = onClickBack)
        )
        Text(
            text = stringResource(R.string.title_kyc_notifications),
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = algerianFontFamily,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
        )
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(
            color = Color.Black,
            thickness = 2.dp,
            modifier = Modifier
                .padding(
                    top = 40.dp,
                    start = 35.dp,
                    end = 35.dp
                )
        )

        when(notificationState.state){
            DisplayState.Idle,
            DisplayState.Success -> {

                val sizeNotifications = notificationState.notifications.size
                if(sizeNotifications > 0){

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 40.dp),
                        contentPadding = PaddingValues(start = 15.dp, top = 20.dp, bottom = 30.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(sizeNotifications) { item ->

                            NotificationCard(notificationState.notifications[item])
                        }
                    }
                }
                else {

                    OutlinedCard(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                        ),
                        border = BorderStroke(width = 1.dp, color = Color.White),
                        shape = RoundedCornerShape(size = 16.dp),
                        modifier = Modifier
                            .padding(start= 50.dp, top=70.dp)
                            .size(
                                width = 300.dp,
                                height = 100.dp
                            )
                    ) {
                        Text(
                            text = stringResource(R.string.title_no_notifications),
                            color = MaterialTheme.colorScheme.onSurface,
                            fontFamily = algerianFontFamily,
                            fontSize = 28.sp,
                            fontWeight =FontWeight.Normal,
                            modifier = Modifier.padding(
                                start = 25.dp,
                                top= 30.dp
                            )
                        )
                    }

                }
            }
            DisplayState.Loading -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
            DisplayState.Exit -> {}
            is DisplayState.Error ->{

                val error = notificationState.state.messageData!!
                val action = NotificationAction.fromAttempts(notificationState.attempts,onClickBack)
                KycAlertDialog(
                    messageData = error,
                    dismissDialog = {onAction(action)})
            }
        }
    }
}


@Preview
@Composable
fun NotificationsScreenPreview(){
    NotificationView(notificationState = NotificationsState(), onClickBack = {})
}