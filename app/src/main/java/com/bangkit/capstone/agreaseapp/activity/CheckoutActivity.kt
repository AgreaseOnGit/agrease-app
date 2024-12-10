package com.bangkit.capstone.agreaseapp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.bangkit.capstone.agreaseapp.ui.component.bottombar.CheckoutBottomBar
import com.bangkit.capstone.agreaseapp.ui.screen.checkout.CheckoutScreen
import com.bangkit.capstone.agreaseapp.ui.theme.AgreaseTheme

class CheckoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val idProduct = intent.getIntExtra("id", 0)

        setContent {
            AgreaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val activity = (LocalContext.current as Activity)
                    val context = LocalContext.current

                    Scaffold(
                        bottomBar = {
                            CheckoutBottomBar(
                                onPayClick = {
                                    activity.startActivity(
                                        Intent(context, SuccessActivity::class.java)
                                    )
                                },
                            )
                        }
                    )
                    { innerPadding ->
                        CheckoutScreen(id = idProduct, modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}