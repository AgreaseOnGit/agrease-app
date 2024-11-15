package com.bangkit.capstone.agreaseapp.ui.screen.home

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bangkit.capstone.agreaseapp.R
import com.bangkit.capstone.agreaseapp.ui.component.ButtonActionMenu
import com.bangkit.capstone.agreaseapp.ui.screen.ViewModelFactory
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(android.graphics.Color.parseColor("#FFFFFF")),
                        Color(android.graphics.Color.parseColor("#00BF63")),
                    )
                )
            )
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Home Screen", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(vertical = 50.dp))
        Column(
            modifier = modifier
                .clip(
                    MaterialTheme.shapes.large.copy(
                        bottomEnd = CornerSize(0.dp),
                        bottomStart = CornerSize(0.dp)
                    )
                )
                .background(Color.White)
                .padding(start = 20.dp, top = 50.dp, end = 20.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}