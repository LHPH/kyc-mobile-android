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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
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


    ObserveAsEvents(viewModel.events){ event ->
        when(event){
            is LoginEvent.onError ->{

            }
        }
    }

    LoginView(loginState, localFocusManager::clearFocus,navigatingToHome,viewModel::onAction)
}

@Composable
fun LoginView(
    loginState: LoginState,
    clearFocus: (Boolean) -> Unit,
    navigatingToHome: () -> Unit,
    onAction: (LoginAction) -> Unit
){
    Box(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit){
            detectTapGestures(onTap={clearFocus(false)})
        }
        .paint(painter = painterResource(id = R.drawable.intro_kyc),
            contentScale = ContentScale.Crop)

    ){

        when(loginState.state) {
            DisplayState.Idle -> {
                Login(Modifier.align(Alignment.Center), loginState, onAction)
            }
            DisplayState.Loading -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
            DisplayState.Success -> {
                navigatingToHome()
            }
            DisplayState.Exit -> {}
            is DisplayState.Error -> {

                val error = loginState.state.messageData!!
                KycAlertDialog(
                    messageData = error,
                    dismissDialog = {
                        onAction(LoginAction.ResetStateToIdle)
                    })
                Login(Modifier.align(Alignment.Center), loginState, onAction)
            }
        }
    }
}

@Composable
fun Login(modifier: Modifier, loginState: LoginState, onAction: (LoginAction) -> Unit){

    Column(modifier = modifier.padding(bottom = 80.dp)){
        HeaderImage(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.padding(5.dp))
        UserField(modifier = modifier,loginState, onAction)
        PasswordField(modifier = modifier,loginState,onAction)
        LoginButton(loginState.loginEnabled){
            onAction(LoginAction.OnClickLogin)
        }
    }
}

@Composable
fun HeaderImage(modifier: Modifier){
    Image(painter = painterResource(id = R.drawable.id_card),
        contentDescription = "Header", modifier = modifier)
}

@Composable
fun UserField(modifier: Modifier, loginState: LoginState,
              onAction: (LoginAction) -> Unit){

    OutlinedTextField(value = loginState.username.value,
        onValueChange = {onAction(LoginAction.OnUsernameChanged(it))},
        isError = loginState.username.error,
        modifier = Modifier.fillMaxWidth()
            .padding(15.dp),
        leadingIcon = { Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary
        )},
        label = {
            Text(
                text = if (loginState.username.error)  stringResource(R.string.label_invalid_username) else  stringResource(R.string.label_username),
                color = if(loginState.username.error) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimary
                )
        },
        placeholder = {
            Text(
                text = stringResource(R.string.placeholder_username),
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
            errorTextColor = MaterialTheme.colorScheme.error,
            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
        singleLine = true,
        maxLines = 1,
    )
}

@Composable
fun PasswordField(modifier: Modifier, loginState: LoginState,
                  onAction: (LoginAction) -> Unit){

    val painterVisibilityIcon = if(loginState.showPassword){
        painterResource(R.drawable.visibility_24px)
    }
    else{
        painterResource(R.drawable.visibility_lock_24px)
    }

    OutlinedTextField(value = loginState.password.value,
        onValueChange = {onAction(LoginAction.OnPasswordChanged(it))},
        isError = loginState.password.error,
        modifier = Modifier.fillMaxWidth()
            .padding(15.dp),
        leadingIcon = { Icon(
            imageVector = Icons.Filled.Lock,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary
        )},
        trailingIcon = {
            IconButton(onClick = {onAction(LoginAction.ShowPassword(loginState.showPassword))}) {
            Icon(
                painter = painterVisibilityIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }},
        label = {
            Text(
                text = if(loginState.password.error) stringResource(R.string.label_invalid_password) else stringResource(R.string.label_password) ,
                color = if(loginState.password.error) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimary
            )
        },
        placeholder = {
            Text(
                text = stringResource(R.string.placeholder_password),
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
            errorTextColor = MaterialTheme.colorScheme.error,
            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary
        ),
        visualTransformation = if (loginState.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
    )
}

@Composable
fun LoginButton(loginEnable: Boolean, onLoginSelected: () -> Unit){

    Button(onClick = {onLoginSelected()},
        enabled =  loginEnable,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = Color.Gray
        ),
        modifier = Modifier
        .fillMaxWidth()
        .padding(15.dp)
        .height(48.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = stringResource(R.string.btn_sign_in),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}


@Preview
@Composable
fun LoginScreenPreview(){

    LoginView(LoginState(), clearFocus = {}, navigatingToHome = {}, onAction = {})
}