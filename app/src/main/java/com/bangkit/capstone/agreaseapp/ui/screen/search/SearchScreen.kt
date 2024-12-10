package com.bangkit.capstone.agreaseapp.ui.screen.search

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.capstone.agreaseapp.R
import com.bangkit.capstone.agreaseapp.activity.DetailProductActivity
import com.bangkit.capstone.agreaseapp.ui.component.product.ProductItem
import com.bangkit.capstone.agreaseapp.ui.component.respond.ErrorMessage
import com.bangkit.capstone.agreaseapp.ui.component.respond.LoadingIndicator
import com.bangkit.capstone.agreaseapp.ui.screen.ViewModelFactory
import com.bangkit.capstone.agreaseapp.ui.state.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    redirectToWelcome: () -> Unit,
    viewModel: SearchViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
) {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    val orientation = LocalConfiguration.current.orientation
    val gridColumns = if (orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 4

    var keyword by remember { mutableStateOf("") }

    val role by viewModel.userRole

    LaunchedEffect(key1 = role) {
        viewModel.getUserRole()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White
            )
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .padding( top = 10.dp, bottom = 20.dp)
        ) {
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                ),
                colors = CardDefaults.elevatedCardColors(
                    Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 15.dp)
            ){
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box (
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        TextField(
                            value = keyword,
                            onValueChange = { keyword = it },
                            shape = RoundedCornerShape(7.dp),
                            placeholder = { Text("Search Product", style = MaterialTheme.typography.titleMedium) },
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Search
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp)
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(painterResource(id = R.drawable.round_search_24), contentDescription = "Search")
                    }
                }
            }
            viewModel.products.collectAsState(initial = UiState.Loading).value.let { products ->
                when (products) {
                    is UiState.Loading -> {
                        LoadingIndicator()
                        viewModel.getProducts()
                    }

                    is UiState.Success -> {
                        val rowCount = (products.data.size + gridColumns - 1) / gridColumns
                        val gridHeight = (300.dp * rowCount) + (10.dp * (rowCount - 1))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 15.dp, end = 15.dp)
                        ) {
                            Text(
                                text = "Recomendation Products",
                                fontSize = 18.sp,
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
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
                                            Intent(context, DetailProductActivity::class.java).apply {
                                                putExtra("id", products.data.indexOf(product))
                                                when(role){
                                                    is UiState.Success -> {
                                                        putExtra("role", (role as UiState.Success<String>).data)
                                                    }
                                                    else -> {}
                                                }
                                            }
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
}