package com.bangkit.capstone.agreaseapp.activity

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.bangkit.capstone.agreaseapp.ui.screen.product.add.AddProductScreen
import com.bangkit.capstone.agreaseapp.ui.screen.product.update.UpdateProductScreen
import com.bangkit.capstone.agreaseapp.ui.theme.AgreaseTheme

class AddProductActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val from = intent.getStringExtra("from")
        val idProduct = if (from == "update") intent.getIntExtra("id", 0) else null

        setContent {
            AgreaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val activity = (LocalContext.current as Activity)
                    val context = LocalContext.current

                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = if (from == "update") "Update Product" else "Sell New Product",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                },
                                navigationIcon = {
                                    IconButton(onClick = { activity.finish() }) {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowBack,
                                            contentDescription = "Back"
                                        )
                                    }
                                }

                            )
                        },
                    )
                    { innerPadding ->
                        if (idProduct != null) {
                            UpdateProductScreen( id = idProduct, modifier = Modifier.padding(innerPadding), redirectToWelcome = {
                                activity.finish()
                            })
                        } else {
                            AddProductScreen( modifier = Modifier.padding(innerPadding), redirectToWelcome = {
                                activity.finish()
                            })
                        }
                    }
                }
            }
        }
    }
}