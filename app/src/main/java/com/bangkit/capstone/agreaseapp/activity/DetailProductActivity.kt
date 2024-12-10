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
import com.bangkit.capstone.agreaseapp.ui.component.bottombar.DetailBottomBar
import com.bangkit.capstone.agreaseapp.ui.screen.product.ProductScreen
import com.bangkit.capstone.agreaseapp.ui.theme.AgreaseTheme

class DetailProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val idProduct = intent.getIntExtra("id", 0)
        val role = intent.getStringExtra("role")

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
                            if (role == "buyer") {
                                DetailBottomBar(
                                    onBuyClicked = {
                                        activity.startActivity(
                                            Intent(context, CheckoutActivity::class.java).putExtra(
                                                "id",
                                                idProduct
                                            )
                                        )
                                    },
//                                onSaveClicked = {}
                                )
                            }
                        }
                    )
                    { innerPadding ->
                        ProductScreen(id = idProduct, modifier = Modifier.padding(innerPadding), redirectToWelcome = {
                            activity.finish()
                        })
                    }
                }
            }
        }
    }
}