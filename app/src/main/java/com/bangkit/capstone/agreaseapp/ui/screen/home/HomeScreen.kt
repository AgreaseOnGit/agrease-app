package com.bangkit.capstone.agreaseapp.ui.screen.home

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bangkit.capstone.agreaseapp.R
import com.bangkit.capstone.agreaseapp.activity.AddProductActivity
import com.bangkit.capstone.agreaseapp.activity.CategoryActivity
import com.bangkit.capstone.agreaseapp.activity.DetailProductActivity
import com.bangkit.capstone.agreaseapp.ui.component.ButtonActionMenu
import com.bangkit.capstone.agreaseapp.ui.component.product.ProductItem
import com.bangkit.capstone.agreaseapp.ui.component.respond.ErrorMessage
import com.bangkit.capstone.agreaseapp.ui.component.respond.LoadingIndicator
import com.bangkit.capstone.agreaseapp.ui.screen.ViewModelFactory
import com.bangkit.capstone.agreaseapp.ui.state.UiState

@Composable
fun HomeScreen(
    redirectToWelcome: () -> Unit,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
) {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    val orientation = LocalConfiguration.current.orientation
    val gridColumns = if (orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 4

    val role by viewModel.userRole

    LaunchedEffect(key1 = role) {
        viewModel.getUserRole()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
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
                            colors = when (role) {
                                is UiState.Success -> {
                                    if ((role as UiState.Success<String>).data == "seller") {
                                        CardDefaults.cardColors(Color(0xFF2C98EE))
                                    } else {
                                        CardDefaults.cardColors(Color(0xFF00BF63))
                                    }
                                }
                                else -> { CardDefaults.cardColors(Color(0xFFFF9800)) }
                            },
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
                                    painter = painterResource(id = R.drawable.agreasewhite),
                                    contentDescription = "Agrease Logo",
                                    modifier = Modifier
                                        .size(60.dp)
                                )
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .weight(1f)
                                ) {
                                    Text(
                                        text = "Hi, ${user.data.nama}",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = "Welcome to Agrease App!",
                                        textAlign = TextAlign.Center,
                                        color = Color.White,
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                }
                                AsyncImage(
                                    model = user.data.imageUrl,
                                    contentDescription = "Profile Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
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
                modifier = Modifier
                    .padding( top = 10.dp, bottom = 20.dp)
            ) {
                when(role) {
                    is UiState.Success -> {
                        if ((role as UiState.Success<String>).data == "buyer") {
                            Text(
                                text = "Product Categories",
                                fontSize = 18.sp,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
                            )
                            Spacer(modifier = Modifier.height(7.dp))
                            viewModel.categories.collectAsState(initial = UiState.Loading).value.let { categories ->
                                when (categories) {
                                    is UiState.Loading -> {
                                        LoadingIndicator()
                                        viewModel.getCategories()
                                    }
                                    is UiState.Success -> {
                                        LazyRow(
                                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 10.dp, end = 10.dp)
                                        ) {
                                            items(categories.data.size) { category ->
                                                ButtonActionMenu(
                                                    text = categories.data[category].name,
                                                    onClick = {
                                                        activity.startActivity(
                                                            Intent(context, CategoryActivity::class.java).apply {
                                                                putExtra("category", categories.data[category].name)
                                                                when(role){
                                                                    is UiState.Success -> {
                                                                        putExtra("role", (role as UiState.Success<String>).data)
                                                                    }
                                                                    else -> {}
                                                                }
                                                            }
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
                        } else {

                        }
                    }
                    else -> {}
                }
                viewModel.products.collectAsState(initial = UiState.Loading).value.let { products ->
                    when (products) {
                        is UiState.Loading -> {
                            LoadingIndicator()
//                            viewModel.getProducts()
                            when(role) {
                                is UiState.Success -> {
                                    if ((role as UiState.Success<String>).data == "seller") {
                                        viewModel.getProducts("seller")
                                    } else {
                                        viewModel.getProducts("buyer")
                                    }
                                }
                                else -> {}
                            }
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
                                    text = when(role) {
                                        is UiState.Success -> {
                                            if ((role as UiState.Success<String>).data == "seller") {
                                                "All Products you sell"
                                            } else {
                                                "Recommendation Products for you"
                                            }
                                        }
                                        else -> { "Recommendation Products for Guest"}
                                    },
                                    fontSize = 18.sp,
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

        when(role){
            is UiState.Success -> {
                if ((role as UiState.Success<String>).data == "seller") {
                    FloatingActionButton(
                        shape = CircleShape,
                        onClick = {
                            activity.startActivity(
                                Intent(context, AddProductActivity::class.java).apply {
                                    putExtra("from", "add")
                                }
                            )
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                    ) {
                        Icon(Icons.Filled.Add, "Add Product")
                    }
                }
            }
            else -> { }
        }
    }
}