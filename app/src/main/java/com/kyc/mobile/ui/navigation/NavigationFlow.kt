package com.kyc.mobile.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kyc.mobile.KycMobileAndroidApplication
import com.kyc.mobile.ui.screens.home.HomeScreen
import com.kyc.mobile.ui.screens.intro.IntroScreen
import com.kyc.mobile.ui.screens.login.LoginScreen
import com.kyc.mobile.ui.viewmodel.HomeViewModel
import com.kyc.mobile.ui.viewmodel.LoginViewModel
import com.kyc.mobile.ui.viewmodel.viewModelFactory

@Composable
fun NavigationFlow(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Intro){

        composable<Intro>{

            IntroScreen(){
                navController.navigate(Login)
            }
        }

        composable<Login>{

            val loginViewModel = viewModel<LoginViewModel>(
                factory = viewModelFactory {
                    LoginViewModel(KycMobileAndroidApplication.appModule.loginRepository)
                }
            )

            LoginScreen(loginViewModel){
                navController.navigate(Home)
            }
        }

        composable<Home>{

            val homeViewModel = viewModel<HomeViewModel>(
                factory = viewModelFactory {
                    HomeViewModel(
                        KycMobileAndroidApplication.appModule.loginRepository,
                        KycMobileAndroidApplication.appModule.dataStoreRepository
                    )
                }
            )

            HomeScreen(
                viewModel = homeViewModel,
                navigateToLogin = {
                    navController.navigate(Login)
                })
        }
    }
}