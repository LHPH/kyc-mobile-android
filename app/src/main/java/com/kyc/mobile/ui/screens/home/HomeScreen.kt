package com.kyc.mobile.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kyc.mobile.R
import com.kyc.mobile.ui.shared.DisplayState
import com.kyc.mobile.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navigateToLogin: ()-> Unit = {},
    navigateToBills: ()->Unit = {},
    navigateToNotifications: () -> Unit = {},
    navigateToPayments: () -> Unit = {}
) {

    val homeState by viewModel.homeState.collectAsStateWithLifecycle()
    HomeScreenView(homeState,viewModel::onAction, navigateToLogin, navigateToBills, navigateToNotifications, navigateToPayments);
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenView(
    homeState: HomeState,
    onAction: (action: HomeAction) -> Unit = {},
    navigateToLogin: ()-> Unit = {},
    navigateToBills: ()->Unit = {},
    navigateToNotifications: () -> Unit = {},
    navigateToPayments: () -> Unit = {}
){
    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon =
                    {
                        Icon(
                            imageVector = Icons.Rounded.Home,
                            contentDescription = null
                        )
                    },
                title = {Text(text = "Welcome ${homeState.customerName}")},
                actions = {
                    AppBarScrollContent(homeState.expandedDropdown,onAction)
                },
            )
        },
        bottomBar = {
            Spacer(modifier = Modifier.width(10.dp))
            NavigationBar() {
                NavigationBarItem(
                    selected = false,
                    onClick = navigateToBills,
                    icon = { Icon(Icons.Filled.Info, null) },
                    label = {
                        Text(
                            text = stringResource(R.string.option_title_bills)
                        )
                    }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = navigateToNotifications,
                    icon = { Icon(Icons.Filled.Notifications, null) },
                    label = {
                        Text(
                            text = stringResource(R.string.option_title_notifications)
                        )
                    }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = navigateToPayments,
                    icon = { Icon(
                        painter = painterResource(R.drawable.paid_24px),
                        contentDescription = ""
                    ) },
                    label = {
                        Text(
                            text = stringResource(R.string.option_title_payments)
                        )
                    }
                )
            }
        }){ paddingValues ->

        when(homeState.state){
            DisplayState.Idle,
            DisplayState.Success -> {
                Box(
                    modifier = Modifier.padding(paddingValues)
                        .fillMaxSize()
                        .paint(
                            painter = painterResource(id = R.drawable.intro_kyc),
                            contentScale = ContentScale.Crop
                        )
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 30.dp),
                        contentPadding = PaddingValues(horizontal = 15.dp, vertical = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(homeState.services.size) { item ->
                            ServiceCardSection(homeState.services[item])
                        }
                    }
                }
            }
            DisplayState.Exit -> {
                navigateToLogin()
            }
            DisplayState.Loading -> {
                Box(modifier = Modifier.padding(paddingValues)
                    .fillMaxSize()
                    .paint(painter = painterResource(id = R.drawable.intro_kyc),
                        contentScale = ContentScale.Crop)
                ){
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            }
            is DisplayState.Error ->{
                val error: DisplayState.Error = homeState as DisplayState.Error
            }
        }
    }
}