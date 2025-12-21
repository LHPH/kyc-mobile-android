package com.kyc.mobile.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kyc.mobile.R
import com.kyc.mobile.ui.shared.DisplayState
import com.kyc.mobile.ui.shared.KycAlertDialog
import com.kyc.mobile.ui.shared.ObserveAsEvents
import com.kyc.mobile.ui.viewmodel.LoginViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel, navigatingToHome: () -> Unit) {

    val localFocusManager = LocalFocusManager.current
    val loginState: LoginState by viewModel.loginState.collectAsStateWithLifecycle()

    Box(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit){
            detectTapGestures(onTap={
                localFocusManager.clearFocus()
            })
        }
        .paint(painter = painterResource(R.drawable.kyc_background),
            contentScale = ContentScale.FillBounds)
    ){

        ObserveAsEvents(viewModel.events){ event ->
            when(event){
                is LoginEvent.onError ->{

                }
            }
        }

        when(loginState.state) {
            DisplayState.Idle -> {
                Login(Modifier.align(Alignment.Center), loginState, viewModel)
            }
            DisplayState.Loading -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
                /*Box(modifier = Modifier.fillMaxSize()) {

                }*/
            }
            DisplayState.Success -> {
                navigatingToHome()
            }
            DisplayState.Exit -> {}
            is DisplayState.Error -> {

                val error = (loginState.state as DisplayState.Error).messageData
                KycAlertDialog(
                    messageData = error,
                    dismissDialog = {
                        viewModel.onAction(LoginAction.ResetStateToIdle)
                    })
                Login(Modifier.align(Alignment.Center), loginState, viewModel)
            }
        }
    }
}

@Composable
fun Login(modifier: Modifier, loginState: LoginState, viewModel: LoginViewModel){

    Column(modifier = modifier){
        HeaderImage(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.padding(16.dp))
        UserField(modifier = modifier,loginState, viewModel)
        PasswordField(modifier = modifier,loginState,viewModel)
        LoginButton(loginState.loginEnabled){
            viewModel.onAction(LoginAction.OnClickLogin)
        }
    }
}

@Composable
fun HeaderImage(modifier: Modifier){
    Image(painter = painterResource(id = R.drawable.icon_account_circle_200),
        contentDescription = "Header", modifier = modifier)
}

@Composable
fun UserField(modifier: Modifier, loginState: LoginState,
              viewModel: LoginViewModel){

    OutlinedTextField(value = loginState.username.value,
        onValueChange = {viewModel.onAction(LoginAction.OnUsernameChanged(it))},
        isError = loginState.username.error,
        modifier = Modifier.fillMaxWidth()
            .padding(15.dp),
        leadingIcon = { Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = null
        )},
        label = {
            Text(
                text = if (loginState.username.error) "The username is invalid" else "Username",
                color = if(loginState.username.error) Color.Red else Color.Black
                )
        },
        placeholder = {
            Text(text = "Username")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
        singleLine = true,
        maxLines = 1,
    )
}

@Composable
fun PasswordField(modifier: Modifier, loginState: LoginState,
                  viewModel: LoginViewModel){
    OutlinedTextField(value = loginState.password.value,
        onValueChange = {viewModel.onAction(LoginAction.OnPasswordChanged(it))},
        isError = loginState.password.error,
        modifier = Modifier.fillMaxWidth()
            .padding(15.dp),
        leadingIcon = { Icon(
            imageVector = Icons.Filled.Lock,
            contentDescription = null
        )},
        trailingIcon = {
            IconButton(onClick = {viewModel.onAction(LoginAction.ShowPassword(loginState.showPassword))}) {
            Icon(
                imageVector = if (loginState.showPassword) Icons.Default.Info else Icons.Default.Info,
                contentDescription = null,
            )
        }},
        label = {
            Text(
                text = if(loginState.password.error) "The password is invalid" else "Password",
                color = if(loginState.password.error) Color.Red else Color.Black
            )
        },
        placeholder = {
            Text(text = "Password")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        visualTransformation = if (loginState.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
    )
}

@Composable
fun LoginButton(loginEnable: Boolean, onLoginSelected: () -> Unit){

    Button(onClick = {onLoginSelected()},
        enabled =  loginEnable,
        modifier = Modifier
        .fillMaxWidth()
        .padding(15.dp)
        .height(48.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(text = "Sign In")
    }
}