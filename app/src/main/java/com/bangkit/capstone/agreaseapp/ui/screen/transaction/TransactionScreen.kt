package com.bangkit.capstone.agreaseapp.ui.screen.transaction

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bangkit.capstone.agreaseapp.data.model.dummy.DummyDataSource
import com.bangkit.capstone.agreaseapp.ui.component.respond.ErrorMessage
import com.bangkit.capstone.agreaseapp.ui.component.respond.LoadingIndicator
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import com.bangkit.capstone.agreaseapp.ui.screen.ViewModelFactory

@Composable
fun TransactionScreen(
    redirectToWelcome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TransactionViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )
) {
    val transactionsState = viewModel.transactions.collectAsState(initial = UiState.Loading).value

    val orientation = LocalConfiguration.current.orientation
    val gridColumns = if (orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 4

    // Mengambil data transaksi dari DummyDataSource
    val transactions = DummyDataSource.dummyTransaction

    // Handling UiState
    when (transactionsState) {
        is UiState.Loading -> {
            LoadingIndicator()
            viewModel.getTransactions()
        }

        is UiState.Success -> {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 20.dp),
                modifier = modifier
                    .fillMaxSize()
                    .heightIn(max = (100.dp * transactionsState.data.size) + (10.dp * (transactionsState.data.size - 1)))
            ) {
                if (transactionsState.data.isEmpty()) {
                    item {
                        ErrorMessage(message = "No Transactions found")
                    }
                } else {
                    items(transactionsState.data) { transaction ->
                        ElevatedCard(
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .clickable { }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White)
                            ) {
                                Text(
                                    text = transaction.shop_name,
                                    maxLines = 1,
                                    fontSize = 15.sp,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    AsyncImage(
                                        model = transaction.image,
                                        contentDescription = "Transaction Image",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .size(100.dp)
                                            .clip(MaterialTheme.shapes.medium)
                                    )

                                    // Detail transaksi
                                    Column(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(start = 8.dp)
                                    ) {
                                        Text(
                                            text = "Product: ${transaction.product_name}",
                                            maxLines = 1,
                                            lineHeight = 20.sp,
                                            fontSize = 15.sp,
                                            color = MaterialTheme.colorScheme.onTertiaryContainer
                                        )
                                        Spacer(modifier = Modifier.height(3.dp))
                                        Text(
                                            text = "Total: ${transaction.total_price}",
                                            maxLines = 1,
                                            lineHeight = 20.sp,
                                            fontSize = 15.sp,
                                            color = MaterialTheme.colorScheme.onTertiaryContainer
                                        )
                                        Text(
                                            text = "Status: ${transaction.status}",
                                            maxLines = 1,
                                            lineHeight = 20.sp,
                                            fontSize = 15.sp,
                                            color = MaterialTheme.colorScheme.onTertiaryContainer
                                        )
                                    }
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = 8.dp, bottom = 8.dp),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    androidx.compose.material3.Button(
                                        onClick = {
                                            // Handle click action here
                                        }
                                    ) {
                                        Text(text = "Lihat Penilaian")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        is UiState.Error -> {
            ErrorMessage(message = transactionsState.errorMessage)
        }

        is UiState.Unauthorized -> {
            redirectToWelcome()
        }
    }
}
