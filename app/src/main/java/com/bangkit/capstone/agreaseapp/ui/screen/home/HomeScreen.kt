package com.bangkit.capstone.agreaseapp.ui.screen.home

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bangkit.capstone.agreaseapp.CategoryActivity
import com.bangkit.capstone.agreaseapp.R
import com.bangkit.capstone.agreaseapp.ui.component.ButtonActionMenu
import com.bangkit.capstone.agreaseapp.ui.component.product.ProductItem
import com.bangkit.capstone.agreaseapp.ui.component.respond.ErrorMessage
import com.bangkit.capstone.agreaseapp.ui.component.respond.LoadingIndicator
import com.bangkit.capstone.agreaseapp.ui.screen.ViewModelFactory
import com.bangkit.capstone.agreaseapp.ui.state.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    redirectToWelcome: () -> Unit,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val orientation = LocalConfiguration.current.orientation
    val gridColumns = if (orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 4

    TopAppBar(
        title = {
            Text(text = "Home Screen")
        },
        actions = {

        }
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White
            )
            .verticalScroll(rememberScrollState())
    ) {
        viewModel.user.collectAsState(initial = UiState.Loading).value.let { user ->
            when (user) {
                is UiState.Loading -> {
                    LoadingIndicator()
                    viewModel.getUser()
                }
                is UiState.Success -> {
                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 7.dp
                        ),
                        colors = CardDefaults.cardColors(Color(0xFF00BF63)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = CardDefaults.shape)
                            .shadow(
                                elevation = 0.dp,
                                spotColor = Color.Transparent,
                                shape = CardDefaults.shape
                            )
                            .padding(bottom = 10.dp, top = 8.dp, start = 8.dp, end = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 15.dp, end = 15.dp, bottom = 10.dp, top = 10.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.agrease_rev),
                                contentDescription = "Agrease Logo",
                                modifier = Modifier
                                    .size(60.dp)
                            )
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .widthIn(max = screenWidth - 200.dp)
                            ) {
                                Text(
                                    text = "Hi, ${user.data.name}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "Welcome to Agrease App!",
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                            AsyncImage(
                                model = user.data.profile,
                                contentDescription = "Profile Image",
                                contentScale = ContentScale.Crop,
                                modifier = modifier
                                    .padding(4.dp)
                                    .size(45.dp)
                                    .width(45.dp)
                                    .clip(CircleShape)
                            )
                        }
                    }
                }
                is UiState.Error -> {
                    ErrorMessage(message = user.errorMessage)
                }
                else -> {}
            }
        }
        Column(
            modifier = modifier
                .padding( top = 10.dp, bottom = 20.dp)
        ) {
            Text(
                text = "Categories",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
            )
            Spacer(modifier = Modifier.height(7.dp))
            viewModel.categories.collectAsState(initial = UiState.Loading).value.let { categories ->
                when (categories) {
                    is UiState.Loading -> {
                        LoadingIndicator()
                        viewModel.getcategories()
                    }
                    is UiState.Success -> {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 10.dp)
                        ) {
                            items(categories.data.size) { category ->
                                ButtonActionMenu(
                                    text = categories.data[category].name,
                                    onClick = {
                                        activity.startActivity(
                                            Intent(context, CategoryActivity::class.java).putExtra(
                                                "category",
                                                categories.data[category].name
                                            )
                                        )
                                    },
                                )
                            }
                        }
                    }
                    is UiState.Error -> {
                        ErrorMessage(message = categories.errorMessage)
                    }
                    is UiState.Unauthorized -> {
                        DisposableEffect(key1 = categories ){
                            redirectToWelcome()
                            onDispose { }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            viewModel.products.collectAsState(initial = UiState.Loading).value.let { products ->
                when (products) {
                    is UiState.Loading -> {
                        LoadingIndicator()
                        viewModel.getProducts(1)
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
                                style = MaterialTheme.typography.titleMedium,
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
                                    name = product.name,
                                    price = product.price,
                                    image = product.image,
                                    rating = product.rating,
                                    onNavigateToDetailScreen = {},
                                    id = product.id,
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