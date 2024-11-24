package com.bangkit.capstone.agreaseapp.ui.screen.profile

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.capstone.agreaseapp.ui.screen.ViewModelFactory

@Composable
fun DetailProfileScreen (
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    modifier: Modifier = Modifier
) {
    var textState by remember { mutableStateOf("Hello") }

    TextField(
        value = textState,
        onValueChange = { textState = it },
        label = { Text("Label") }
    )
}

@Preview(showBackground = true)
@Composable
fun DetailProfileScreenPreview() {
    DetailProfileScreen()
}