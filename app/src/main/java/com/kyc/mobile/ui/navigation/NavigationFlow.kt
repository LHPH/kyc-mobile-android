package com.kyc.mobile.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.google.firebase.analytics.FirebaseAnalytics
import com.kyc.mobile.KycMobileAndroidApplication
import com.kyc.mobile.domain.model.CustomerBill
import com.kyc.mobile.ui.screens.bills.BillDetailScreen
import com.kyc.mobile.ui.screens.bills.BillScreen
import com.kyc.mobile.ui.screens.home.HomeScreen
import com.kyc.mobile.ui.screens.intro.IntroScreen
import com.kyc.mobile.ui.screens.login.LoginScreen
import com.kyc.mobile.ui.screens.notifications.NotificationsScreen
import com.kyc.mobile.ui.screens.permissions.PermissionsScreen
import com.kyc.mobile.ui.viewmodel.BillViewModel
import com.kyc.mobile.ui.viewmodel.HomeViewModel
import com.kyc.mobile.ui.viewmodel.IntroScreenViewModel
import com.kyc.mobile.ui.viewmodel.LoginViewModel
import com.kyc.mobile.ui.viewmodel.NotificationsViewModel
import com.kyc.mobile.ui.viewmodel.PermissionsViewModel
import com.kyc.mobile.ui.viewmodel.viewModelFactory
import kotlin.reflect.typeOf

@Composable
fun NavigationFlow(){

    val navController = rememberNavController()
    val appContext = LocalContext.current.applicationContext
    
    val appModule = KycMobileAndroidApplication.appModule
    val firebaseModule = appModule.firebaseModule
    val featureModule = appModule.featureModule
    val googleServicesModule = appModule.googleServicesModule
    val dataStoreModule = appModule.dataStoreModule
    
    val analyticsManager = firebaseModule.analyticsManager
    val remoteConfigManager = firebaseModule.remoteConfigManager
    val propertyRepository = dataStoreModule.propertiesRepository
    val publicRepository = featureModule.publicRepository
    val loginRepository = featureModule.loginRepository
    val customerTrackActionRepository = featureModule.customerTrackActionRepository
    val locationRepository = googleServicesModule.location
    val dataStoreRepository = dataStoreModule.dataStoreRepository
    val customerApplicationRepository = featureModule.customerApplicationRepository
    val customerNotificationRepository = featureModule.customerNotificationRepository
    val customerBillsRepository = featureModule.customerBillsRepository
    val publicKeyRepository = featureModule.publicKeyRepository
    

    navController.addOnDestinationChangedListener { _, destination, _ ->

        val params = HashMap<String,String>()
        val route = destination.route ?: "Route NA"
        params[FirebaseAnalytics.Param.SCREEN_NAME] = route
        params[FirebaseAnalytics.Param.SCREEN_CLASS] = route
        analyticsManager.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW,params)
    }

    NavHost(navController = navController, startDestination = Permissions){

        composable<Permissions>{

            val permissionsViewModel = viewModel<PermissionsViewModel>(
                factory = viewModelFactory {
                    PermissionsViewModel(appContext)
                }
            )

            PermissionsScreen(
                permissionsViewModel = permissionsViewModel,
                onGranted = {
                    navController.navigate(Intro)
                }
            )
        }

        composable<Intro>{

            val introScreenViewModel = viewModel<IntroScreenViewModel>(
                factory = viewModelFactory {
                    IntroScreenViewModel(
                        remoteConfigManager,
                        publicKeyRepository
                    )
                }
            )

            IntroScreen(
                viewModel = introScreenViewModel,
                onStartClick = {
                    navController.navigate(Login)
                }
            )
        }

        composable<Login>{

            val loginViewModel = viewModel<LoginViewModel>(
                factory = viewModelFactory {
                    LoginViewModel(
                        appContext,
                        loginRepository,
                        customerTrackActionRepository,
                        analyticsManager,
                        locationRepository
                    )
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
                        loginRepository,
                        customerApplicationRepository,
                        customerTrackActionRepository,
                        dataStoreRepository
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
                        customerNotificationRepository
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
                        customerBillsRepository
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