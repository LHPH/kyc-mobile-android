package com.kyc.mobile.ui.screens.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kyc.mobile.R
import com.kyc.mobile.ui.shared.DisplayState
import com.kyc.mobile.ui.shared.KycAlertDialog
import com.kyc.mobile.ui.theme.algerianFontFamily
import com.kyc.mobile.ui.viewmodel.IntroScreenViewModel

@Composable
fun IntroScreen(
    viewModel: IntroScreenViewModel,
    onStartClick: ()-> Unit = {}
){

    val splashScreenText by viewModel.splashScreenText.collectAsStateWithLifecycle()
    val introScreenState by viewModel.introScreenState.collectAsStateWithLifecycle()
    IntroScreenView(splashScreenText,
        introScreenState,viewModel::onAction,onStartClick)
}

@Composable
fun IntroScreenView(
    splashScreenText: String,
    introScreenState: IntroScreenState,
    onAction: (IntroScreenAction) -> Unit = {},
    onStartClick: ()-> Unit = {}
){
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (image, btn, title) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.intro_kyc),
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
            text = splashScreenText,
            fontFamily = algerianFontFamily,
            color = Color.White,
            fontSize = 26.sp,
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
                .clickable(
                    enabled = introScreenState.state is DisplayState.Success,
                    onClick = {onAction(IntroScreenAction.OnClickButton(onStartClick))}
                ),
            contentAlignment = Alignment.Center
        ){

            if(introScreenState.state is DisplayState.Success){

                Text(
                    text = stringResource(R.string.btn_gets_started),
                    fontFamily = algerianFontFamily,
                    color = Color.White,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 22.sp,
                    modifier = Modifier.padding(start = 18.dp)
                )
            }
            else{
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                        .size(30.dp),
                    color = Color.White,
                    strokeWidth = 3.0.dp
                )
            }
        }
    }

    if(introScreenState.state is DisplayState.Error){

        val error = introScreenState.state.messageData!!
        KycAlertDialog(
            messageData = error,
            dismissDialog = {
                onAction(IntroScreenAction.ResetStateToIdle)
            })
    }
}

@Preview
@Composable
fun IntroScreenPreview(){
    IntroScreenView(
        splashScreenText = "Your APP to manage your operations with KYC",
        introScreenState = IntroScreenState(),
        onAction = {},
        onStartClick = {})
}