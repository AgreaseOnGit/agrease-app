package com.bangkit.capstone.agreaseapp.ui.screen.transaction

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bangkit.capstone.agreaseapp.R
import com.bangkit.capstone.agreaseapp.activity.DetailTransactionActivity
import com.bangkit.capstone.agreaseapp.ui.component.respond.ErrorMessage
import com.bangkit.capstone.agreaseapp.ui.component.respond.LoadingIndicator
import com.bangkit.capstone.agreaseapp.ui.screen.ViewModelFactory
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TransactionScreen(
    redirectToWelcome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TransactionViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )
) {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    val role by viewModel.userRole

    fun formattedPrice(price: Int): String {
        return NumberFormat.getNumberInstance(Locale("id", "ID")).format(price)
    }

    LaunchedEffect(key1 = role) {
        viewModel.getUserRole()
    }


    viewModel.transactions.collectAsState(initial = UiState.Loading).value.let { transactions ->
        when (transactions) {
            is UiState.Loading -> {
                LoadingIndicator()
                when(role) {
                    is UiState.Success -> {
                        if ((role as UiState.Success<String>).data == "seller") {
                            viewModel.getTransactions("seller")
                        } else {
                            viewModel.getTransactions("buyer")
                        }
                    }
                    else -> {}
                }
            }

            is UiState.Success -> {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .background(Color.White),
                ) {
                    items(transactions.data.size) { transaction ->
                        ElevatedCard(
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .clickable {
                                    activity.startActivity(
                                        Intent(
                                            context,
                                            DetailTransactionActivity::class.java
                                        ).apply {
                                            putExtra("id", transaction)
                                            when(role){
                                                is UiState.Success -> {
                                                    putExtra("role", (role as UiState.Success<String>).data)
                                                }
                                                else -> {}
                                            }
                                        }
                                    )
                                }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 20.dp,
                                            top = 10.dp,
                                            end = 15.dp,
                                            bottom = 5.dp
                                        ),
                                ) {
                                    when(role) {
                                        is UiState.Success -> {
                                            if ((role as UiState.Success<String>).data == "seller") {
                                                ElevatedCard(
                                                    elevation = CardDefaults.cardElevation(
                                                        defaultElevation = 15.dp
                                                    ),
                                                    colors = when (transactions.data[transaction].status) {
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
                                                        text = transactions.data[transaction].status,
                                                        fontSize = 12.sp,
                                                        color = Color.White,
                                                        modifier = Modifier.padding(vertical = 2.dp, horizontal = 7.dp)
                                                    )
                                                }
                                            } else {
                                                Image(
                                                    painter = painterResource(id = R.drawable.baseline_add_business_24),
                                                    colorFilter = ColorFilter.tint(Color(0xFF00BF63)),
                                                    contentDescription = "shop",
                                                    modifier = Modifier
                                                        .size(20.dp)
                                                )
                                                Spacer(modifier = Modifier.width(5.dp))
                                                Text(
                                                    text = transactions.data[transaction].product.sellerId.toUpperCase(Locale.getDefault()),
                                                    fontSize = 15.sp,
                                                    style = MaterialTheme.typography.titleMedium,
                                                    color = Color(0xFF00BF63),
                                                )
                                            }
                                        }
                                        else -> {}
                                    }
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(
                                        text = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID")).format(transactions.data[transaction].date),
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(vertical = 2.dp, horizontal = 7.dp)
                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(110.dp)
                                        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                                ) {
                                    AsyncImage(
                                        model = transactions.data[transaction].product.image,
                                        contentDescription = "Transaction Image",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .size(100.dp)
                                            .clip(MaterialTheme.shapes.medium)
                                    )

                                    Column(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(start = 5.dp)
                                    ) {
                                        Text(
                                            text = transactions.data[transaction].product.productName.toUpperCase(
                                                Locale.getDefault()),
                                            maxLines = 2,
                                            fontSize = 15.sp,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.onTertiaryContainer
                                        )
                                        when(role) {
                                            is UiState.Success -> {
                                                if ((role as UiState.Success<String>).data == "seller") {
                                                    Text(
                                                        text = "Buyer : ${transactions.data[transaction].idBuyer}",
                                                        maxLines = 1,
                                                        fontSize = 15.sp,
                                                        color = MaterialTheme.colorScheme.onTertiaryContainer
                                                    )
                                                } else {
                                                    Spacer(modifier = Modifier.weight(1f))
                                                }
                                                Text(
                                                    text = "Total : Rp ${formattedPrice(transactions.data[transaction].total)}",
                                                    maxLines = 1,
                                                    fontSize = 15.sp,
                                                    color = MaterialTheme.colorScheme.onTertiaryContainer
                                                )
                                                if ((role as UiState.Success<String>).data == "buyer") {
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        verticalAlignment = Alignment.Bottom,
                                                        horizontalArrangement = Arrangement.SpaceBetween
                                                    ) {
                                                        Text(
                                                            text = "${transactions.data[transaction].quantity} Items",
                                                            fontSize = 15.sp,
                                                            color = MaterialTheme.colorScheme.onTertiaryContainer
                                                        )
                                                        ElevatedCard(
                                                            elevation = CardDefaults.cardElevation(
                                                                defaultElevation = 15.dp
                                                            ),
                                                            colors = when (transactions.data[transaction].status) {
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
                                                                text = transactions.data[transaction].status,
                                                                fontSize = 12.sp,
                                                                color = Color.White,
                                                                modifier = Modifier.padding(vertical = 2.dp, horizontal = 7.dp)
                                                            )
                                                        }
                                                    }
                                                } else {
                                                    Text(
                                                        text = "${transactions.data[transaction].quantity} Items",
                                                        fontSize = 15.sp,
                                                        color = MaterialTheme.colorScheme.onTertiaryContainer
                                                    )
                                                }

                                            }
                                            else -> {}
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            is UiState.Error -> {
                ErrorMessage(message = transactions.errorMessage)
            }

            is UiState.Unauthorized -> {
                redirectToWelcome()
            }
        }
    }
}
