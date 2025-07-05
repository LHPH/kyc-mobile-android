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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kyc.mobile.R
import com.kyc.mobile.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(viewModel: LoginViewModel) {

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .paint(painter = painterResource(R.drawable.kyc_background),
            contentScale = ContentScale.FillWidth)
    ){
        Login(Modifier.align(Alignment.Center),viewModel)
    }
}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel){

    val username: String by viewModel.username.observeAsState("")
    val password: String by viewModel.password.observeAsState("")
    val loginEnabled: Boolean by viewModel.isLoginEnabled.observeAsState(false);
    val isLoading: Boolean by viewModel.isLoading.observeAsState(false);

    val coroutineScope = rememberCoroutineScope();

    if(isLoading){
        Box(modifier = Modifier.fillMaxSize()){
            CircularProgressIndicator(Modifier.align(Alignment.Center));
        }
    }
    else{
        Column(modifier = modifier){
            HeaderImage(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(16.dp))
            UserField(modifier = modifier,username, {viewModel.onLoginChanged(it, password)})
            Spacer(modifier = Modifier.padding(4.dp))
            PasswordField(modifier = modifier,password, {viewModel.onLoginChanged(username, it)})
            Spacer(modifier = Modifier.padding(16.dp))
            LoginButton(loginEnabled){
                coroutineScope.launch {
                    viewModel.onLoginSelected()
                }
            }
        }
    }
}

@Composable
fun HeaderImage(modifier: Modifier){
    Image(painter = painterResource(id = R.drawable.icon_account_circle_200),
        contentDescription = "Header", modifier = modifier)
}

@Composable
fun UserField(modifier: Modifier, username: String, onTextFieldChanged: (String) -> Unit){

    TextField(value = username,
        onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
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
fun PasswordField(modifier: Modifier, password: String, onTextFieldChanged: (String) -> Unit){
    TextField(value = password,
        onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
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
fun LoginButton(loginEnable: Boolean, onLoginSelected: () -> Unit){

    Button(onClick = {onLoginSelected()},
        enabled =  loginEnable,
        modifier = Modifier
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