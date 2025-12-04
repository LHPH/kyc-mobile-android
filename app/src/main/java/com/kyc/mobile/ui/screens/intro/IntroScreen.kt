package com.kyc.mobile.ui.screens.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.kyc.mobile.R

@Composable
@Preview
fun IntroScreen(
    onStartClick: ()-> Unit = {}
){

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (image, btn, title) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.intro_pic),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxSize()
        )
        Text(
            text = "Your APP to manage your operations with KYC",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(title) {
                bottom.linkTo(btn.bottom, margin = 58.dp)
                start.linkTo(btn.start,margin = 9.dp)
            }
        )
        Box(
            modifier = Modifier
                .constrainAs(btn) {
                    start.linkTo(parent.start, margin = 32.dp)
                    bottom.linkTo(parent.bottom, margin = 60.dp)
                }
                .width(135.dp)
                .height(50.dp)
                .border(
                    width = 2.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(size = 12.dp)
                )
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(size = 12.dp)
                )
                .clickable { onStartClick() },
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Gets Started",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 22.sp
            )
        }
    }

}