package com.bangkit.capstone.agreaseapp.ui.screen.transaction.detail

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import com.bangkit.capstone.agreaseapp.activity.DetailProductActivity
import com.bangkit.capstone.agreaseapp.ui.component.product.ProductItem
import com.bangkit.capstone.agreaseapp.ui.component.respond.ErrorMessage
import com.bangkit.capstone.agreaseapp.ui.component.respond.LoadingIndicator
import com.bangkit.capstone.agreaseapp.ui.screen.ViewModelFactory
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DetailTransactionScreen(
    id: Int,
    redirectToWelcome: () -> Unit,
    viewModel: DetailTransactionViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    modifier: Modifier
) {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    val shippingFee = 20000

    fun formattedPrice(price: Int): String {
        return NumberFormat.getNumberInstance(Locale("id", "ID")).format(price)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White
            )
            .padding(start = 10.dp, end = 10.dp, top = 15.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp)
                .clickable {
                    // Handle click event
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 8.dp, horizontal = 15.dp)
            ) {
                viewModel.transaction.collectAsState(initial = UiState.Loading).value.let { transaction ->
                    when(transaction){
                        is UiState.Loading -> {
                            LoadingIndicator()
                            viewModel.getDetailTransactions(id)
                        }
                        is UiState.Success -> {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Order Status :",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                                ElevatedCard(
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 15.dp
                                    ),
                                    colors = when (transaction.data.status) {
                                        "Done" -> CardDefaults.cardColors(Color(0xFF00BF63))
                                        "On Delivery" -> CardDefaults.cardColors(Color(0xFF2C98EE))
                                        "Packaging" -> CardDefaults.cardColors(Color(0xFFFF9800))
                                        else -> CardDefaults.cardColors(Color(0xFFE91E1E))
                                    },
                                    modifier = Modifier
                                        .clip(shape = CardDefaults.shape)
                                        .shadow(
                                            elevation = 0.dp,
                                            spotColor = Color.Transparent,
                                            shape = CardDefaults.shape
                                        )
                                ){
                                    Text(
                                        text = transaction.data.status,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 7.dp)
                                    )
                                }
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Date :",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                                Text(
                                    text = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID")).format(transaction.data.date),
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(vertical = 2.dp, horizontal = 7.dp)
                                )
                            }
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White)
                                    .padding(horizontal = 5.dp, vertical = 5.dp),
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                                thickness = 1.dp
                            )
                        }
                        is UiState.Error -> {
                            ErrorMessage(message = transaction.errorMessage)
                        }
                        else -> {}
                    }
                }
                viewModel.user.collectAsState(initial = UiState.Loading).value.let { user ->
                    when(user){
                        is UiState.Loading -> {
                            LoadingIndicator()
                            viewModel.getUser()
                        }
                        is UiState.Success -> {
                            Text(
                                text = "Delivery Address :",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                            )
                            Text(
                                text = user.data.address,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                            )
                        }
                        is UiState.Error -> {
                            ErrorMessage(message = user.errorMessage)
                        }
                        else -> {}
                    }
                }
            }
        }
        viewModel.transaction.collectAsState(initial = UiState.Loading).value.let { transaction ->
            when(transaction){
                is UiState.Loading -> {
                    LoadingIndicator()
                    viewModel.getDetailTransactions(id)
                }
                is UiState.Success -> {
                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp, bottom = 10.dp)
                    ){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 5.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, bottom = 5.dp)
                            ) {
                                Text(
                                    text = "Product Details",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,

                                    )
                                Spacer(modifier = Modifier.weight(1f))
                                Image(
                                    painter = painterResource(id = R.drawable.baseline_add_business_24),
                                    colorFilter = ColorFilter.tint(Color(0xFF00BF63)),
                                    contentDescription = "shop",
                                    modifier = Modifier
                                        .size(23.dp)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = transaction.data.product.sellerId.toUpperCase(Locale.getDefault()),
                                    fontSize = 15.sp,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color(0xFF00BF63),
                                )
                            }
                            ElevatedCard(
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 2.dp
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 15.dp)
                            ){
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp)
                                        .background(Color.White)
                                        .padding(4.dp)
                                ) {
                                    AsyncImage(
                                        model = transaction.data.product.image,
                                        contentDescription = "${transaction.data.product.id} Image",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .padding(2.dp)
                                            .aspectRatio(1f)
                                            .clip(MaterialTheme.shapes.medium)
                                    )
                                    Column(
                                        modifier = Modifier
                                            .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
                                            .fillMaxHeight()
                                    ) {
                                        Text(
                                            text = transaction.data.product.productName.toUpperCase(Locale.getDefault()),
                                            maxLines = 2,
                                            lineHeight = 20.sp,
                                            fontSize = 15.sp,
                                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                                        )
                                        Spacer(modifier = Modifier.height(3.dp))
                                        Text(
                                            text = "Rp ${formattedPrice(transaction.data.product.price)}",
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Light,
                                            fontStyle = FontStyle.Italic,
                                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .padding(end = 10.dp)
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.rating),
                                                contentDescription = "rating",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .size(14.dp)
                                            )
                                            Spacer(modifier = Modifier.width(5.dp))
                                            Text(
                                                text = "(${transaction.data.product.rating})",
                                                fontSize = 13.sp,
                                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                            )
                                            Spacer(modifier = Modifier.weight(1f))
                                            Text(
                                                text = "${transaction.data.quantity} Items",
                                                fontSize = 15.sp,
                                                color = MaterialTheme.colorScheme.onTertiaryContainer
                                            )

                                        }
                                    }
                                }
                            }
                            Text(
                                text = "Delivery Information",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                modifier = Modifier.padding(start = 10.dp, bottom = 5.dp)
                            )
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Courier",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "Courier - Express",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Transaction ID",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = transaction.data.id,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }

                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp, bottom = 10.dp)
                    ){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(15.dp)
                        ) {
                            Text(
                                text = "Payment Details",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                modifier = Modifier.padding(start = 10.dp, bottom = 5.dp)
                            )
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Payment Method",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = transaction.data.method,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Total Price (${transaction.data.quantity} items)",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "Rp ${formattedPrice(transaction.data.product.price * transaction.data.quantity)}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Shipping Fee",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "Rp ${formattedPrice(shippingFee)}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Service Fee",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "Rp ${
                                        if(transaction.data.method == "Cash On Delivery (COD)") {
                                            formattedPrice(2500)
                                        } else {
                                            formattedPrice(5000)
                                        }
                                    }",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                            }
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp, horizontal = 5.dp),
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                                thickness = 1.dp
                            )
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Total Payment",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "Rp ${
                                        formattedPrice(transaction.data.product.price * transaction.data.quantity + shippingFee +
                                                if(transaction.data.method == "Cash On Delivery (COD)") {
                                                    2500
                                                } else {
                                                    5000
                                                })
                                    }",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    ErrorMessage(message = transaction.errorMessage)
                }
                else -> {}
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 5.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
            thickness = 1.dp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Recomendation For You",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(7.dp))
        viewModel.products.collectAsState(initial = UiState.Loading).value.let { products ->
            when (products) {
                is UiState.Loading -> {
                    LoadingIndicator()
                    viewModel.getProducts()
                }
                is UiState.Success -> {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        items(products.data.size) { product ->
                            ProductItem(
                                name = products.data[product].productName,
                                price = products.data[product].price,
                                image = products.data[product].image,
                                rating = products.data[product].rating,
                                seller = products.data[product].sellerId,
                                onNavigateToDetailScreen = {
                                    activity.startActivity(
                                        Intent(context, DetailProductActivity::class.java).putExtra(
                                            "id",
                                            product
                                        )
                                    )
                                },
                                id = product,
                            )
                        }
                    }
                }
                is UiState.Error -> {
                    ErrorMessage(message = products.errorMessage)
                }
                is UiState.Unauthorized -> {
                    DisposableEffect(key1 = products ){
                        redirectToWelcome()
                        onDispose { }
                    }
                }

            }
        }
    }

}