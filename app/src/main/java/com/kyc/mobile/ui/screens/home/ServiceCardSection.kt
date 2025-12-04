package com.kyc.mobile.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyc.mobile.R

@Preview
@Composable
fun ServiceCardSection(onClick: ()-> Unit = {}){

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(230.dp)
            .fillMaxWidth()
            .clip(
                shape = RoundedCornerShape(size = 16.dp)
            )
            .clickable{onClick()}
    ){
        Image(
            painter = painterResource(id = R.drawable.card),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
        )
        Image(
            painter = painterResource(id = R.drawable.medal),
            contentDescription = "",
            modifier = Modifier
                .align(alignment = Alignment.TopEnd)
                .padding(all = 16.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.kyc_logo_icon),
            contentDescription = "",
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .padding(all = 16.dp)
        )
        Text(
            text = "Service 1",
            color = Color.White,
            fontSize = 25.sp,
            modifier = Modifier
                .align(alignment = Alignment.CenterStart)
                .padding(start = 16.dp, bottom = 16.dp)
        )
    }
}