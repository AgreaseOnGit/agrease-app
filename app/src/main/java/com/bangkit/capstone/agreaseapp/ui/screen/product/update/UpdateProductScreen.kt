package com.bangkit.capstone.agreaseapp.ui.screen.product.update

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bangkit.capstone.agreaseapp.data.model.ProductModel
import com.bangkit.capstone.agreaseapp.ui.component.respond.LoadingIndicator
import com.bangkit.capstone.agreaseapp.ui.screen.ViewModelFactory
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class UpdateProductScreen {
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProductScreen(
    id: Int,
    redirectToWelcome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateProductViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
) {
    var submit by remember { mutableStateOf("Submit") }
    var error by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    var product by remember { mutableStateOf(ProductModel("","","", 0.0,"",0,0,"","")) }

    var productName by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("") }
    val categories = listOf("Obat Tanaman", "Produk Segar", "Peralatan Pertanian", "Logistik Pertanian", "Teknologi Pertanian", "Produk Olahan","Perlengkapan Pembibitan","Bibit Tanaman","Pupuk Tanaman","Mesin Pertanian")

    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitMap = remember { mutableStateOf<Bitmap?>(null) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            imageUri = it
        }

    fun createCustomTempFile(context: Context): File {
        val filesDir = context.externalCacheDir
        return File.createTempFile("product", ".jpg", filesDir)
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

    LaunchedEffect(key1 = product) {
        viewModel.getDetail(id)
    }

    viewModel.product.collectAsState(initial = UiState.Loading).value.let { respond ->
        when (respond) {
            is UiState.Loading -> {
                LoadingIndicator()
            }

            is UiState.Success -> {
                product = respond.data
                if (submit == "Loading...") message = "Success"
                submit = "Submit"

                DisposableEffect(key1 = product){
                    if (productName == "") productName = product.productName
                    if (productDescription == "") productDescription = product.productDescription
                    if (price == "") price = product.price.toString()
                    if (image == "") image = product.image
                    if (stock == "") stock = product.stock.toString()
                    if (selectedCategory == "") selectedCategory = product.category

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
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        if(error.isNotEmpty() || message.isNotEmpty()) {
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
        }

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
                        contentDescription = "Product Image",
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
                    model = image,
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .padding(4.dp)
                        .size(120.dp)
                        .clip(RectangleShape)
                        .clickable {
                            launcher.launch("image/*")
                        }
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            "Product Image",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = productName,
            onValueChange = { productName = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            label = { Text("Product Name", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant) },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = productDescription,
            onValueChange = { productDescription = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
            ),
            maxLines = 10,
            label = { Text("Product Description", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant) },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            label = { Text("Price", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant) },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = stock,
            onValueChange = { stock = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            label = { Text("Stock", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant) },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            OutlinedTextField(
                value = selectedCategory,
                onValueChange = { selectedCategory = it },
                readOnly = true,
                label = { Text("Select Product Category", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            selectedCategory = category
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .padding(start = 10.dp, end = 10.dp),
        ) {
            Text(
                submit,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}