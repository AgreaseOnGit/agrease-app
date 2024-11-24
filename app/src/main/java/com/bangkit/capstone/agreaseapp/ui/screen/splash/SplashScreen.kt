package com.bangkit.capstone.agreaseapp.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.capstone.agreaseapp.R
import com.bangkit.capstone.agreaseapp.ui.screen.auth.RegisterScreen
import com.bangkit.capstone.agreaseapp.ui.theme.AgreaseTheme
import com.bangkit.capstone.agreaseapp.ui.theme.custom_green
import kotlinx.coroutines.delay

@Composable
fun SplashScreen (onTimeout: () -> Unit) {

    LaunchedEffect(Unit) {
        delay(6000L)
        onTimeout()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(custom_green)
//            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Image(
                painter = painterResource(R.drawable.logo), // Replace with your app logo
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
//                    .size(300.dp)
                    .align(Alignment.CenterHorizontally)
//                    .clip(CircleShape)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    AgreaseTheme {
        SplashScreen(onTimeout = {})
    }
}