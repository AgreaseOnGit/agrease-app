package com.bangkit.capstone.agreaseapp.ui.screen.category

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.capstone.agreaseapp.activity.DetailProductActivity
import com.bangkit.capstone.agreaseapp.ui.component.product.ProductItem
import com.bangkit.capstone.agreaseapp.ui.component.respond.ErrorMessage
import com.bangkit.capstone.agreaseapp.ui.component.respond.LoadingIndicator
import com.bangkit.capstone.agreaseapp.ui.screen.ViewModelFactory
import com.bangkit.capstone.agreaseapp.ui.state.UiState

@Composable
fun CategoryScreen(
    category: String,
    redirectToWelcome: () -> Unit,
    viewModel: CategoryViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    
    val orientation = LocalConfiguration.current.orientation
    val gridColumns = if (orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 4

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = Color.White
            )
            .verticalScroll(rememberScrollState())
    ) {
        viewModel.products.collectAsState(initial = UiState.Loading).value.let { products ->
            when (products) {
                is UiState.Loading -> {
                    LoadingIndicator()
                    viewModel.getProducts(category)
                }

                is UiState.Success -> {
                    val rowCount = (products.data.size + gridColumns - 1) / gridColumns
                    val gridHeight = (300.dp * rowCount) + (10.dp * (rowCount - 1))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp)
                    ) {
                        Text(
                            text = "Hasil : ${products.data.size} products",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                    Spacer(modifier = Modifier.height(7.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(gridColumns),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .heightIn(max = gridHeight)
                    ) {
                        items(products.data) { product ->
                            ProductItem(
                                name = product.productName,
                                price = product.price,
                                image = product.image,
                                rating = product.rating,
                                seller = product.sellerId,
                                onNavigateToDetailScreen = {
                                    activity.startActivity(
                                        Intent(context, DetailProductActivity::class.java).putExtra(
                                            "id",
                                            products.data.indexOf(product)
                                        )
                                    )
                                },
                                id = products.data.indexOf(product),
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