package com.kyc.mobile.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.kyc.mobile.KycMobileAndroidApplication
import com.kyc.mobile.domain.model.CustomerBill
import com.kyc.mobile.ui.screens.bills.BillDetailScreen
import com.kyc.mobile.ui.screens.bills.BillScreen
import com.kyc.mobile.ui.screens.home.HomeScreen
import com.kyc.mobile.ui.screens.intro.IntroScreen
import com.kyc.mobile.ui.screens.login.LoginScreen
import com.kyc.mobile.ui.screens.notifications.NotificationsScreen
import com.kyc.mobile.ui.viewmodel.BillViewModel
import com.kyc.mobile.ui.viewmodel.HomeViewModel
import com.kyc.mobile.ui.viewmodel.LoginViewModel
import com.kyc.mobile.ui.viewmodel.NotificationsViewModel
import com.kyc.mobile.ui.viewmodel.viewModelFactory
import kotlin.reflect.typeOf

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
                    LoginViewModel(
                        KycMobileAndroidApplication.appModule.featureModule.loginRepository,
                        KycMobileAndroidApplication.appModule.featureModule.customerTrackActionRepository)
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
                        KycMobileAndroidApplication.appModule.featureModule.loginRepository,
                        KycMobileAndroidApplication.appModule.featureModule.customerApplicationRepository,
                        KycMobileAndroidApplication.appModule.featureModule.customerTrackActionRepository,
                        KycMobileAndroidApplication.appModule.dataStoreModule.dataStoreRepository
                    )
                }
            )

            HomeScreen(
                viewModel = homeViewModel,
                navigateToLogin = {
                    navController.navigate(Login)
                },
                navigateToBills = {
                    navController.navigate(Bills)
                },
                navigateToNotifications = {
                    navController.navigate(Notifications)
                },
                navigateToPayments = {
                }
            )
        }

        composable<Notifications>{

            val notificationViewModel= viewModel<NotificationsViewModel>(
                factory = viewModelFactory {
                    NotificationsViewModel(
                        KycMobileAndroidApplication.appModule.featureModule.customerNotificationRepository
                    )
                }
            )

            NotificationsScreen(
                notificationsViewModel = notificationViewModel,
                onClickBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<Bills>{

            val billViewModel = viewModel<BillViewModel>(
                factory = viewModelFactory {
                    BillViewModel(
                        KycMobileAndroidApplication.appModule.featureModule.customerBillsRepository
                    )
                }
            )

            BillScreen(
                viewModel = billViewModel,
                onClickBack = {
                    navController.popBackStack()
                },
                onClickDetail = { bill ->
                    navController.navigate(BillDetail(bill))
                }
            )
        }

        composable<BillDetail>(
            typeMap = mapOf(
                typeOf<CustomerBill>() to createNavType<CustomerBill>()
            )
        ){ backStackEntry ->
            val args: BillDetail = backStackEntry.toRoute<BillDetail>()
            BillDetailScreen(
                args.bill,
                onClickBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}