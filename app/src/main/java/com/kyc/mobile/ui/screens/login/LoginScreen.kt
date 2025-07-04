package com.kyc.mobile.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kyc.mobile.R

@Composable
fun LoginScreen() {

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .paint(painter = painterResource(R.drawable.kyc_background),
            contentScale = ContentScale.FillWidth)
    ){
        Login(Modifier.align(Alignment.Center))
    }
}

@Composable
fun Login(modifier: Modifier){

    Column(modifier = modifier){
        HeaderImage(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.padding(16.dp))
        UserField(modifier = modifier)
        Spacer(modifier = Modifier.padding(4.dp))
        PasswordField(modifier = modifier)
        Spacer(modifier = Modifier.padding(16.dp))
        LoginButton()
    }
}

@Composable
fun HeaderImage(modifier: Modifier){
    Image(painter = painterResource(id = R.drawable.icon_account_circle_200),
        contentDescription = "Header", modifier = modifier)
}

@Composable
fun UserField(modifier: Modifier){
    TextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = "Username")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFFFFFFFF),
            errorTextColor = Color(0xFFE71F1F),
            focusedContainerColor = Color(0xFFEFE3B7),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun PasswordField(modifier: Modifier){
    TextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = "Password")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFFFFFFFF),
            errorTextColor = Color(0xFFE71F1F),
            focusedContainerColor = Color(0xFFEFE3B7),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun LoginButton(){

    Button(onClick = {}, modifier = Modifier
        .fillMaxWidth()
        .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFD5C89C),
            disabledContainerColor = Color.Gray,
            contentColor = Color.White,
            disabledContentColor = Color.White
        )
    ) {
        Text(text = "Sign In")
    }
}