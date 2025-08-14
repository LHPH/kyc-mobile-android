package com.kyc.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.kyc.mobile.di.AppModule
import com.kyc.mobile.di.AppModuleImpl
import com.kyc.mobile.ui.navigation.NavigationFlow
import com.kyc.mobile.ui.theme.KycMobileAndroidTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge();

        setContent {
            KycMobileAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                        .paint(painter = painterResource(R.drawable.kyc_background),
                            contentScale = ContentScale.Fit),
                    //color = MaterialTheme.colorScheme.background
                    ){
                    NavigationFlow()
                }
            }
        }
    }
}