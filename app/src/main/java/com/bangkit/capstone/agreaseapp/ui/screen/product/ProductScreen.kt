package com.bangkit.capstone.agreaseapp.ui.screen.product

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bangkit.capstone.agreaseapp.R
import com.bangkit.capstone.agreaseapp.activity.AddProductActivity
import com.bangkit.capstone.agreaseapp.ui.component.respond.ErrorMessage
import com.bangkit.capstone.agreaseapp.ui.component.respond.LoadingIndicator
import com.bangkit.capstone.agreaseapp.ui.screen.ViewModelFactory
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ProductScreen(
    id: Int,
    redirectToWelcome: () -> Unit,
    viewModel: ProductViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    modifier: Modifier
) {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    val role by viewModel.userRole

    fun formattedPrice(price: Long): String {
        return NumberFormat.getNumberInstance(Locale("id", "ID")).format(price)
    }

    LaunchedEffect(key1 = role) {
        viewModel.getUserRole()
    }

    viewModel.product.collectAsState(initial = UiState.Loading).value.let { product ->
        when (product) {
            is UiState.Loading -> {
                LoadingIndicator(modifier = Modifier)
                when(role) {
                    is UiState.Success -> {
                        if ((role as UiState.Success<String>).data == "seller") {
                            viewModel.getDetail("seller",id)
                        }
                        if ((role as UiState.Success<String>).data == "buyer") {
                            viewModel.getDetail("buyer",id)
                        }
                    }
                    else -> {}
                }
            }

            is UiState.Success -> {
                Box (
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        item {
                            product.data.apply {
                                AsyncImage(
                                    model = image,
                                    contentDescription = "$id Image",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f),
                                    alignment = Alignment.CenterStart,
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                        item {
                            product.data.apply {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                                ) {
                                    Text(
                                        text = productName.toUpperCase(Locale.getDefault()),
                                        style = MaterialTheme.typography.titleLarge,
                                    )
                                    Spacer(modifier = Modifier.height(5.dp))
                                    when (role) {
                                        is UiState.Success -> {
                                            if ((role as UiState.Success<String>).data == "seller") {
                                                Text(
                                                    text = "Rp ${formattedPrice(price)}",
                                                    fontSize = 18.sp,
                                                    fontWeight = FontWeight.Light,
                                                    fontStyle = FontStyle.Italic,
                                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                                )
                                                Row {
                                                    Text(
                                                        text = "Sold : 45 Items",
                                                        fontSize = 18.sp,
                                                        fontWeight = FontWeight.Light,
                                                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                                                    )
                                                    Spacer(modifier = Modifier.weight(1f))
                                                    Text(
                                                        text = "Stock : $stock Items",
                                                        fontSize = 18.sp,
                                                        fontWeight = FontWeight.Light,
                                                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                                                    )
                                                }
                                            } else {
                                                Text(
                                                    text = "Rp ${formattedPrice(price)}",
                                                    fontSize = 18.sp,
                                                    fontWeight = FontWeight.Light,
                                                    fontStyle = FontStyle.Italic,
                                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                                )
                                            }
                                        }
                                        else -> { }
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(0.dp, 5.dp, 0.dp, 5.dp),
                                    ) {
                                        when (role) {
                                            is UiState.Success -> {
                                                Row {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.baseline_add_business_24),
                                                        colorFilter = ColorFilter.tint(
                                                            if ((role as UiState.Success<String>).data == "seller") {
                                                                Color(0xFF2C98EE)
                                                            } else {
                                                                Color(0xFF00BF63)
                                                            }
                                                        ),
                                                        contentDescription = "shop",
                                                        modifier = Modifier
                                                            .size(25.dp)
                                                    )
                                                    Spacer(modifier = Modifier.width(5.dp))
                                                    Text(
                                                        text = sellerId.toUpperCase(Locale.getDefault()),
                                                        fontSize = 18.sp,
                                                        style = MaterialTheme.typography.titleMedium,
                                                        color = if ((role as UiState.Success<String>).data == "seller") {
                                                            Color(0xFF2C98EE)
                                                        } else {
                                                            Color(0xFF00BF63)
                                                        },
                                                    )
                                                }
                                            }
                                            else -> { }
                                        }
                                        Spacer(modifier = Modifier.weight(1f))
                                        Row() {
                                            Image(
                                                painter = painterResource(id = R.drawable.rating),
                                                contentDescription = "rating",
                                                modifier = Modifier
                                                    .size(15.dp)
                                            )
                                            Spacer(modifier = Modifier.width(5.dp))
                                            Text(
                                                text = "($rating)",
                                                style = MaterialTheme.typography.bodyMedium,
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        item {
                            product.data.apply {
                                ElevatedCard(
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 2.dp
                                    ),
                                    colors = CardDefaults.cardColors(Color.White),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 10.dp,
                                            end = 10.dp,
                                            top = 10.dp,
                                            bottom = 75.dp
                                        )
                                ){
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(15.dp)
                                    ) {
                                        Text(
                                            text = "Product Description :",
                                            fontSize = 20.sp,
                                            style = MaterialTheme.typography.titleMedium,
                                        )
                                        Text(
                                            text = productDescription,
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            textAlign = TextAlign.Justify
                                        )
                                    }
                                }
                            }
                        }
                    }
                    when(role){
                        is UiState.Success -> {
                            if ((role as UiState.Success<String>).data == "seller") {
                                FloatingActionButton(
                                    shape = CircleShape,
                                    onClick = {
                                        activity.startActivity(
                                            Intent(context, AddProductActivity::class.java).apply {
                                                putExtra("from", "update")
                                                putExtra("id", id)
                                            }
                                        )
                                    },
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(16.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                                    ) {
                                        Text(
                                            text = "Update Product",
                                            fontSize = 18.sp,
                                            style = MaterialTheme.typography.titleMedium,
                                        )
                                        Spacer(modifier = Modifier.width(5.dp))
                                        Icon(Icons.Filled.Edit, "Update Product")
                                    }
                                }
                            }
                        }
                        else -> { }
                    }
                }
            }

            is UiState.Error -> {
                ErrorMessage(message = product.errorMessage, modifier = Modifier)
            }

            is UiState.Unauthorized -> {
                redirectToWelcome()
            }
        }
    }
}
