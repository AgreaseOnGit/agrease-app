package com.bangkit.capstone.agreaseapp.ui.screen.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bangkit.capstone.agreaseapp.R
import com.bangkit.capstone.agreaseapp.ui.component.respond.ErrorMessage
import com.bangkit.capstone.agreaseapp.ui.component.respond.LoadingIndicator
import com.bangkit.capstone.agreaseapp.ui.screen.ViewModelFactory
import com.bangkit.capstone.agreaseapp.ui.state.UiState

@Composable
fun ProductScreen(
    id: String,
    redirectToWelcome: () -> Unit,
    viewModel: ProductViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    modifier: Modifier = Modifier
) {

    viewModel.products.collectAsState(initial = UiState.Loading).value.let { product ->
        when (product) {
            is UiState.Loading -> {
                LoadingIndicator(modifier = modifier)
                viewModel.getDetail(id)
            }

            is UiState.Success -> {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                ) {
                    item {
                        product.data.apply {
                            AsyncImage(
                                model = "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
                                contentDescription = "$id Image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1.5f),
                                alignment = Alignment.CenterStart,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    item {
                        product.data.apply {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .padding(20.dp)
                            ) {
                                Text(
                                    text = "Product Id $$id",
                                    style = MaterialTheme.typography.titleLarge,
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(0.dp, 5.dp, 0.dp, 5.dp),
                                ) {
                                    Text(
                                        text = price,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontStyle = FontStyle.Italic,
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Row {
                                        Image(
                                            painter = painterResource(id = R.drawable.rating),
                                            contentDescription = "rating",
                                            modifier = Modifier
                                                .size(15.dp)
                                        )
                                        Spacer(modifier = Modifier.width(5.dp))
                                        Text(
                                            text = "($rating)",
                                            style = MaterialTheme.typography.bodyMedium,
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        product.data.apply {
                            Text(
                                text = "Description",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp),
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "This is a product description. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp, 0.dp, 20.dp, 0.dp),
                                textAlign = TextAlign.Justify
                            )
                        }
                    }
                }
            }

            is UiState.Error -> {
                ErrorMessage(message = product.errorMessage, modifier = modifier)
            }

            is UiState.Unauthorized -> {
                redirectToWelcome()
            }
        }
    }

}