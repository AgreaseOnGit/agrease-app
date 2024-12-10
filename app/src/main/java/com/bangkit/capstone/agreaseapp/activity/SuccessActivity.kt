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
import com.bangkit.capstone.agreaseapp.ui.component.bottombar.SuccessBottomBar
import com.bangkit.capstone.agreaseapp.ui.screen.checkout.SuccessScreen
import com.bangkit.capstone.agreaseapp.ui.theme.AgreaseTheme

class SuccessActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AgreaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val activity = (LocalContext.current as Activity)

                    Scaffold(
                        bottomBar = {
                            SuccessBottomBar(
                                onClick = {
                                    val intent = Intent(activity, MainActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    activity.finish()
                                },
                            )
                        }
                    )
                    { innerPadding ->
                        SuccessScreen( modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}