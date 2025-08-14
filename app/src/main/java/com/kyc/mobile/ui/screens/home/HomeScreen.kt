package com.kyc.mobile.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kyc.mobile.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    Box(modifier = Modifier
        .fillMaxSize()
        .paint(painter = painterResource(R.drawable.kyc_background),
            contentScale = ContentScale.FillBounds)
        ) {
        TopAppBar(
            navigationIcon =
                {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = null
                    )
                },
            title = {Text(text = "Menu")}
        )

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 15.dp, vertical = 150.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            items(5) {
                ListItem(
                    headlineContent = {
                        Text(text = "Servicio")
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null
                        )
                    }
                )
            }
        }
       /* OutlinedCard(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            border = BorderStroke(3.dp, MaterialTheme.colorScheme.primary),
            modifier = Modifier.size(width = 300.dp, height = 130.dp)
                .align(Alignment.Center)
        ) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp)
            )
            Text(
                text = "Service",
                modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }*/
        Spacer(modifier = Modifier.width(10.dp))
        NavigationBar(modifier = Modifier.align(Alignment.BottomEnd)) {
            NavigationBarItem(
                selected = true,
                onClick = { },
                icon = { Icon(Icons.Filled.Info, null) },
                label = { Text(text = "Bills") }
            )
            NavigationBarItem(
                selected = true,
                onClick = { },
                icon = { Icon(Icons.Filled.Notifications, null) },
                label = { Text(text = "Notifications") }
            )
            NavigationBarItem(
                selected = true,
                onClick = { },
                icon = { Icon(Icons.Filled.Favorite, null) },
                label = { Text(text = "Payments") }
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}