package com.kyc.mobile.ui.screens.home

//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyc.mobile.R
import com.kyc.mobile.domain.model.CustomerContractService
import com.kyc.mobile.domain.util.DateUtil
import com.kyc.mobile.domain.util.GeneralUtil
import com.kyc.mobile.ui.theme.algerianFontFamily
import java.time.format.DateTimeFormatter

@Composable
fun ServiceCardSection(
    contractService: CustomerContractService,
    onClick: ()-> Unit = {}
){

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(230.dp)
            .fillMaxWidth()
            .clip(
                shape = RoundedCornerShape(size = 16.dp)
            )
            .background(color = MaterialTheme.colorScheme.surfaceContainerHighest)
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(size = 16.dp)
            )
            .clickable{onClick()}
    ){
        Icon(
            painter = painterResource(id = R.drawable.star_24px),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .align(alignment = Alignment.TopEnd)
                .padding(all = 16.dp)
        )
        Text(
            text = "KYC",
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = algerianFontFamily,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .padding(all = 16.dp)
        )
        Text(
            text = contractService.service,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .padding(bottom = 16.dp)
        )
        Text(
            text = GeneralUtil.doubleValueToStringFormat(contractService.cost),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 15.sp,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .padding(top = 40.dp)
        )
        Text(
            text = DateUtil.parseLocalDateTimeToStringFormat(contractService.creationDate,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_LOCAL_DATE),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 15.sp,
            modifier = Modifier
                .align(alignment = Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 10.dp)
        )
        Text(
            text = "Folio: ${contractService.folio}",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 15.sp,
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(end = 18.dp, bottom = 10.dp)
        )
    }
}