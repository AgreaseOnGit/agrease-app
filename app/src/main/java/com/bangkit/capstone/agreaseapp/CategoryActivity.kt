package com.bangkit.capstone.agreaseapp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
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
import com.bangkit.capstone.agreaseapp.ui.screen.category.CategoryScreen
import com.bangkit.capstone.agreaseapp.ui.theme.AgreaseTheme

class CategoryActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val category = intent.getStringExtra("category" )

        setContent {
            AgreaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val activity = (LocalContext.current as? Activity)

                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(text = "Product Category") },
                                navigationIcon = {
                                    IconButton(onClick = { activity?.finish() }) {
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
                        CategoryScreen(category = category!!, modifier = Modifier.padding(innerPadding), redirectToWelcome = {
                            activity?.finish()
                        })
                    }
                }
            }
        }
    }
}