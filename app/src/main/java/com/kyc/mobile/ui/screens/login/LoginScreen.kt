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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.kyc.mobile.R
import com.kyc.mobile.ui.shared.KycAlertDialog
import com.kyc.mobile.ui.viewmodel.LoginViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel, navigatingToHome: () -> Unit) {

    Box(modifier = Modifier
        .fillMaxSize()
        .paint(painter = painterResource(R.drawable.kyc_background),
            contentScale = ContentScale.FillBounds)
    ){
        Login(Modifier.align(Alignment.Center),viewModel,navigatingToHome)
    }
}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel, navigatingToHome: () -> Unit){

    val username: String by viewModel.username.observeAsState("")
    val password: String by viewModel.password.observeAsState("")
    val loginEnabled: Boolean by viewModel.isLoginEnabled.observeAsState(false);
    val errorUsername: Boolean by viewModel.errorUsername.observeAsState(false);
    val errorPassword: Boolean by viewModel.errorPassword.observeAsState(false);
    val loginState: LoginState by viewModel.loginState.collectAsState();
    val showPassword: Boolean by viewModel.showPassword.observeAsState(false);
    val context = LocalContext.current;

    val coroutineScope = rememberCoroutineScope();

    when(loginState){
        LoginState.Idle -> {

            Column(modifier = modifier){
                HeaderImage(Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.padding(16.dp))
                UserField(modifier = modifier,username,errorUsername, {viewModel.onUsernameChanged(it)})
                PasswordField(modifier = modifier,password,errorPassword,showPassword,
                    {viewModel.onPasswordChanged(it)}, {viewModel.showPasswordOnScreen(it)})
                LoginButton(loginEnabled){
                    viewModel.login()
                }
            }
        }
        LoginState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()){
                CircularProgressIndicator(Modifier.align(Alignment.Center));
            }
        }
        LoginState.Success -> {
            navigatingToHome()
        }
        is LoginState.Error -> {

            val error: LoginState.Error = loginState as LoginState.Error
            KycAlertDialog(messageData = error.messageData, dismissDialog = {viewModel.resetToIdleState()})
            Column(modifier = modifier){
                HeaderImage(Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.padding(16.dp))
                UserField(modifier = modifier,username,errorUsername, {viewModel.onUsernameChanged(it)})
                PasswordField(modifier = modifier,password,errorPassword,showPassword,
                    {viewModel.onPasswordChanged(it)},{viewModel.showPasswordOnScreen(it)})
                LoginButton(loginEnabled){
                    viewModel.login()
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
fun UserField(modifier: Modifier, username: String,errorUsername: Boolean,
              onTextFieldChanged: (String) -> Unit){

    OutlinedTextField(value = username,
        onValueChange = {onTextFieldChanged(it)},
        isError = errorUsername,
        modifier = Modifier.fillMaxWidth()
            .padding(15.dp),
        leadingIcon = { Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = null
        )},
        label = {
            Text(
                text = if (errorUsername) "The username is invalid" else "Username",
                color = if(errorUsername) Color.Red else Color.Black
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
fun PasswordField(modifier: Modifier, password: String, errorPassword: Boolean,
                  showPassword: Boolean,
                  onTextFieldChanged: (String) -> Unit,
                  onDisplayPassword: (Boolean)-> Unit){
    OutlinedTextField(value = password,
        onValueChange = {onTextFieldChanged(it)},
        isError = errorPassword,
        modifier = Modifier.fillMaxWidth()
            .padding(15.dp),
        leadingIcon = { Icon(
            imageVector = Icons.Filled.Lock,
            contentDescription = null
        )},
        trailingIcon = {
            IconButton(onClick = {onDisplayPassword(showPassword)}) {
            Icon(
                imageVector = if (showPassword) Icons.Default.Info else Icons.Default.Info,
                contentDescription = null,
            )
        }},
        label = {
            Text(
                text = if(errorPassword) "The password is invalid" else "Password",
                color = if(errorPassword) Color.Red else Color.Black
            )
        },
        placeholder = {
            Text(text = "Password")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
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