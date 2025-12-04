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
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kyc.mobile.R
import com.kyc.mobile.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navigateToLogin: ()-> Unit
) {

    val homeState by viewModel.homeState.collectAsStateWithLifecycle()
    val nameCustomer by viewModel.nameCustomer.collectAsStateWithLifecycle("")

    LaunchedEffect(Unit) {
        viewModel.getUserPreferences()
    }

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
                title = {Text(text = "Welcome $nameCustomer")},
                actions = {
                    AppBarScrollContent(viewModel)
                },
            )
        },
        bottomBar = {
            Spacer(modifier = Modifier.width(10.dp))
            NavigationBar() {
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Icon(Icons.Filled.Info, null) },
                    label = { Text(text = "Bills") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Icon(Icons.Filled.Notifications, null) },
                    label = { Text(text = "Notifications") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Icon(Icons.Filled.Favorite, null) },
                    label = { Text(text = "Payments") }
                )
            }
        }){ paddingValues ->

        when(homeState){
            HomeState.Idle -> {
                Box(
                    modifier = Modifier.padding(paddingValues)
                        .fillMaxSize()
                        .paint(
                            painter = painterResource(R.drawable.kyc_background),
                            contentScale = ContentScale.FillBounds
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
                        items(5) {
                            ServiceCardSection()
                            /*ListItem(
                                headlineContent = {
                                    Text(text = "Servicio")
                                },
                                leadingContent = {
                                    Icon(
                                        imageVector = Icons.Filled.Star,
                                        contentDescription = null
                                    )
                                }
                            )*/
                        }
                    }
                }
            }
            HomeState.Exit -> {
                navigateToLogin()
            }
            HomeState.Loading -> {
                Box(modifier = Modifier.padding(paddingValues)
                    .fillMaxSize()){
                    CircularProgressIndicator(Modifier.align(Alignment.Center));
                }
            }
            is HomeState.Error ->{
                val error: HomeState.Error = homeState as HomeState.Error
            }
        }


    }
}