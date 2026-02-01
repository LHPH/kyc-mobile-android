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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyc.mobile.R
import com.kyc.mobile.ui.theme.algerianFontFamily

@Preview(showBackground = false)
@Composable
fun NotificationCard(){

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
                painter = painterResource(id = R.drawable.info_24px),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .padding(start = 5.dp, top = 5.dp)
            )
            Text(
                text = "Welcome to KYC",
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = algerianFontFamily,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .padding(start = 10.dp, top = 35.dp)
            )
            Text(
                text = "2026-10-10",
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