package com.bangkit.capstone.agreaseapp.ui.screen.transaction

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
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
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 20.dp),
                    modifier = modifier
                        .fillMaxSize()
                        .heightIn(max =(100.dp * transactions.data.size) + (10.dp * (transactions.data.size - 1)))
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
                                        top = 5.dp,
                                        bottom = 5.dp,
                                        start = 5.dp,
                                        end = 5.dp,
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
