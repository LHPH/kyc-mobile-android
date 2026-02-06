package com.kyc.mobile.ui.screens.notifications

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyc.mobile.R
import com.kyc.mobile.domain.model.CustomerNotification
import com.kyc.mobile.domain.util.NotificationEventEnum
import com.kyc.mobile.ui.theme.algerianFontFamily


@Composable
fun NotificationCard(notification: CustomerNotification){

    val event = runCatching { enumValueOf<NotificationEventEnum>(notification.event) }
        .getOrDefault(NotificationEventEnum.WARN)
    val painterIconEvent: Painter = when(event){
        NotificationEventEnum.INFO ->{
            painterResource(id = R.drawable.info_24px)
        }
        NotificationEventEnum.WARN->{
            painterResource(id = R.drawable.warning_24px)
        }
        NotificationEventEnum.ERROR->{
            painterResource(id = R.drawable.error_24px)
        }
    }

    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
        ),
        border = BorderStroke(width = 1.dp, color = Color.White),
        shape = RoundedCornerShape(size = 16.dp),
        modifier = Modifier.size(
            width = 300.dp,
            height = 100.dp
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .align(alignment = Alignment.CenterHorizontally)
        ){
            Icon(
                painter = painterIconEvent,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .padding(start = 5.dp, top = 5.dp)
            )
            Text(
                text = notification.message,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = algerianFontFamily,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .padding(start = 10.dp, top = 35.dp)
            )
            Text(
                text = notification.date,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = algerianFontFamily,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                .align(alignment = Alignment.BottomStart)
                .padding(all = 10.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = false)
fun NotificationCardPreview(){
    NotificationCard(CustomerNotification(message = "Welcome to KYC", event = "INFO", date = "2029-10-10"))
}