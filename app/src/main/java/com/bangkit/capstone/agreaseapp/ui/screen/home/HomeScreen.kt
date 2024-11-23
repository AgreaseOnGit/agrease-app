package com.bangkit.capstone.agreaseapp.ui.screen.home

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
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
                color = MaterialTheme.colorScheme.background,
            )
    ) {
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
            viewModel.user.collectAsState(initial = UiState.Loading).value.let { user ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp, bottom = 10.dp, top = 10.dp)
                ) {
                    when (user) {
                        is UiState.Loading -> {
                            LoadingIndicator()
                            viewModel.getUser()
                        }
                        is UiState.Success -> {
                            Image(
                                painter = painterResource(id = R.drawable.agrease_rev),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(60.dp)
                            )
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text(
                                    text = "Hi, ${user.data.name}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
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
                                    .clip(CircleShape)
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
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp)
        ) {
            Text(
                text = "Categories",
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "See All",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
        ) {
            items(10) { index ->
                ButtonActionMenu(
                    text = "Cat ${index + 1}",
                    icon = R.drawable.baseline_web,
                    onClick = {},
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
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
        Spacer(modifier = Modifier.height(10.dp))
        viewModel.products.collectAsState(initial = UiState.Loading).value.let { products ->
            when (products) {
                is UiState.Loading -> {
                    LoadingIndicator()
                    viewModel.getProducts(1)
                }

                is UiState.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
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
                        when (products) {
                            is UiState.Unauthorized -> {
                                redirectToWelcome()
                            }
                            else -> {}
                        }
                        onDispose {  }
                    }
                }
            }
        }
    }
}