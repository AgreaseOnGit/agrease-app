package com.bangkit.capstone.agreaseapp.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bangkit.capstone.agreaseapp.AgreaseApp
import com.bangkit.capstone.agreaseapp.ui.theme.AgreaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            AgreaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    AgreaseApp()
                }
            }
        }
    }
}