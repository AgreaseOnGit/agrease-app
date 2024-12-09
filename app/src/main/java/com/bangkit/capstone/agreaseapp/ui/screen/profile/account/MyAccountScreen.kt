package com.bangkit.capstone.agreaseapp.ui.screen.profile.account

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bangkit.capstone.agreaseapp.data.model.UserModel
import com.bangkit.capstone.agreaseapp.ui.component.respond.LoadingIndicator
import com.bangkit.capstone.agreaseapp.ui.screen.ViewModelFactory
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@Composable
fun MyAccountScreen(
    redirectToWelcome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyAccountViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
) {
    var submit by remember { mutableStateOf("Submit") }
    var error by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var photo by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var user by remember { mutableStateOf(UserModel("", "", "", "", "", "", "",false)) }
    var message by remember { mutableStateOf("") }

    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitMap = remember { mutableStateOf<Bitmap?>(null) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            imageUri = it
        }

    fun createCustomTempFile(context: Context): File {
        val filesDir = context.externalCacheDir
        return File.createTempFile("profile", ".jpg", filesDir)
    }

    fun uriToFile(uri: Uri): File {
        val myFile = createCustomTempFile(context)
        val inputStream = context.contentResolver.openInputStream(uri) as InputStream
        val outputStream = FileOutputStream(myFile)
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer, 0, length)
        outputStream.close()
        inputStream.close()

        return myFile

    }

    LaunchedEffect(key1 = user) {
        viewModel.getUser()
    }

    viewModel.user.collectAsState(initial = UiState.Loading).value.let { respond ->
        when (respond) {
            is UiState.Loading -> {
                LoadingIndicator()
            }

            is UiState.Success -> {
                user = respond.data
                if (submit == "Loading...") message = "Update Success"
                submit = "Submit"

                DisposableEffect(key1 = user){
                    if (name == "") name = user.nama
                    if (email == "") email = user.email
                    if (photo == "") photo = user.photo

                    onDispose { }
                }
            }

            is UiState.Error -> {
                error = respond.errorMessage
                submit = "Submit"
            }

            is UiState.Unauthorized -> {
                DisposableEffect(key1 = respond) {
                    redirectToWelcome()
                    onDispose { }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(
            text =  message,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Green
        )
        Text(
            text = error,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Red
        )

        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitMap.value = MediaStore.Images.Media.getBitmap(
                    context.contentResolver,
                    imageUri
                )
            } else {
                val source =
                    ImageDecoder.createSource(context.contentResolver, imageUri!!)
                bitMap.value = ImageDecoder.decodeBitmap(source)
            }

            bitMap.value?.let {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                ) {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Profile Image",
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .padding(4.dp)
                            .size(150.dp)
                            .clip(CircleShape)
                            .clickable {
                                launcher.launch("image/*")
                            }
                    )
                }
            }
        }

        if (imageUri == null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
            ) {
                AsyncImage(
                    model = photo,
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .padding(4.dp)
                        .size(150.dp)
                        .clip(CircleShape)
                        .clickable {
                            launcher.launch("image/*")
                        }
                )
            }
        }
        Text("Name")
        TextField(
            value = name,
            onValueChange = { name = it },
            label = {  },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Email")
        TextField(
            value = email,
            onValueChange = { email = it },
            label = {  },
            enabled = false,
            leadingIcon = { Icon(Icons.Filled.MailOutline, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(submit)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyAccountScreenPreview() {
    MyAccountScreen(
        redirectToWelcome = {},
        modifier = Modifier
    )
}
