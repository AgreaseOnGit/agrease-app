package com.bangkit.capstone.agreaseapp.ui.screen.transaction

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bangkit.capstone.agreaseapp.R
import com.bangkit.capstone.agreaseapp.ui.component.product.ProductItem
import com.bangkit.capstone.agreaseapp.ui.component.respond.ErrorMessage
import com.bangkit.capstone.agreaseapp.ui.component.respond.LoadingIndicator
import com.bangkit.capstone.agreaseapp.ui.screen.ViewModelFactory
import com.bangkit.capstone.agreaseapp.ui.state.UiState

class TransactionScreen {
}

@Composable
fun TransactionScreen(
    redirectToWelcome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TransactionViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val orientation = LocalConfiguration.current.orientation
    val gridColumns = if (orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 4


    viewModel.transactions.collectAsState(initial = UiState.Loading).value.let { transactions ->
        when (transactions) {
            is UiState.Loading -> {
                LoadingIndicator()
                viewModel.getTransactions()
            }

            is UiState.Success -> {
                LazyColumn (
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                    modifier = modifier
                        .fillMaxSize()
                ) {
                    if (transactions.data.isEmpty()) {
                        item {
                            ErrorMessage(message = "No Transactions found")
                        }
                    } else {
                        items(transactions.data.size){
                            ElevatedCard(
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 4.dp
                                ),

                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 10.dp,
                                        bottom = 8.dp,
                                        start = 10.dp,
                                        end = 10.dp,
                                    )
                                    .clickable { }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .background(Color.White)
                                ) {
                                    AsyncImage(
                                        model = "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
                                        contentDescription = "DummyImage",
                                        contentScale = ContentScale.Crop,
                                        modifier = modifier
                                            .padding(4.dp)
                                            .size(100.dp)
                                            .clip(MaterialTheme.shapes.medium)
                                    )
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        modifier = modifier
                                            .fillMaxWidth()
                                            .padding(10.dp)
                                    ) {
                                        Text(
                                            text = "Transaction ${it + 1}",
                                            maxLines = 3,
                                            lineHeight = 20.sp,
                                            fontSize = 15.sp,
                                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                                        )
                                        Spacer(modifier = Modifier.height(3.dp))
                                        Text(
                                            text = "Transaction ${it + 1}",
                                            maxLines = 3,
                                            lineHeight = 20.sp,
                                            fontSize = 15.sp,
                                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                                        )
                                        Text(
                                            text = "Transaction ${it + 1}",
                                            maxLines = 3,
                                            lineHeight = 20.sp,
                                            fontSize = 15.sp,
                                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                                        )
                                    }
                                }
                            }
                        }
//                        item {
//                            val rowCount = (transactions.data.size + gridColumns - 1) / gridColumns
//                            val gridHeight = (300.dp * rowCount) + (10.dp * (rowCount - 1))
//                            LazyVerticalGrid(
//                                columns = GridCells.Fixed(gridColumns),
//                                horizontalArrangement = Arrangement.spacedBy(14.dp),
//                                verticalArrangement = Arrangement.spacedBy(10.dp),
//                                modifier = Modifier
//                                    .padding(horizontal = 10.dp)
//                                    .heightIn(max = gridHeight)
//                            ) {
//                                items(transactions.data) { transaction ->
//                                    ProductItem(
//                                        name = transaction.name,
//                                        price = transaction.price,
//                                        image = transaction.image,
//                                        rating = transaction.rating,
//                                        onNavigateToDetailScreen = {},
//                                        id = transaction.id,
//                                    )
//                                }
//                            }
//                        }
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
