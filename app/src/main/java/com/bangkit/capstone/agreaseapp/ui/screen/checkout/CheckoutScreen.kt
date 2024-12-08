package com.bangkit.capstone.agreaseapp.ui.screen.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bangkit.capstone.agreaseapp.R
import com.bangkit.capstone.agreaseapp.ui.component.respond.ErrorMessage
import com.bangkit.capstone.agreaseapp.ui.component.respond.LoadingIndicator
import com.bangkit.capstone.agreaseapp.ui.screen.ViewModelFactory
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CheckoutScreen(
    id: Int,
    redirectToWelcome: () -> Unit,
    viewModel: CheckoutViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    modifier: Modifier = Modifier
) {
    val (selectedPaymentMethod, setSelectedPaymentMethod) = remember { mutableStateOf("Cash On Delivery (COD)") }
    val (quantity, setQuantity) = remember { mutableIntStateOf(1) }
    val shippingFee = 20000

    fun formattedPrice(price: Int): String {
        return NumberFormat.getNumberInstance(Locale("id", "ID")).format(price)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White
            )
            .padding(start = 10.dp, end = 10.dp, top = 15.dp)
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
                            defaultElevation = 2.dp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp)
                            .clickable {
                                // Handle click event
                            }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(vertical = 15.dp, horizontal = 20.dp)
                        ) {
                            Text(
                                text = "Shipping Address :",
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Row {
                                Image(
                                    painter = painterResource(id = R.drawable.baseline_location_pin_24),
                                    contentDescription = "address",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clip(MaterialTheme.shapes.medium)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "${user.data.nama} Home",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                            }
                            Text(
                                text = user.data.address,
                                maxLines = 3,
                                lineHeight = 20.sp,
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
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
        viewModel.product.collectAsState(initial = UiState.Loading).value.let { product ->
            when (product) {
                is UiState.Loading -> {
                    LoadingIndicator()
                    viewModel.getDetail(id)
                }
                is UiState.Success -> {
                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp)
                    ){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 5.dp)
                        ) {
                            Text(
                                text = "${product.data.sellerId.toUpperCase(Locale.getDefault())}'s Products",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                modifier = Modifier.padding(start = 10.dp, bottom = 5.dp)
                            )
                            ElevatedCard(
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 2.dp
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 10.dp)
                            ){
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp)
                                        .background(Color.White)
                                        .padding(4.dp)
                                ) {
                                    AsyncImage(
                                        model = product.data.image,
                                        contentDescription = "${product.data.id} Image",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .padding(2.dp)
                                            .aspectRatio(1f)
                                            .clip(MaterialTheme.shapes.medium)
                                    )
                                    Column(
                                        modifier = Modifier
                                            .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
                                            .fillMaxHeight()
                                    ) {
                                        Text(
                                            text = product.data.productName.toUpperCase(Locale.getDefault()),
                                            maxLines = 2,
                                            lineHeight = 20.sp,
                                            fontSize = 15.sp,
                                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                                        )
                                        Spacer(modifier = Modifier.height(3.dp))
                                        Text(
                                            text = "Rp ${formattedPrice(product.data.price)}",
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Light,
                                            fontStyle = FontStyle.Italic,
                                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .padding(end = 10.dp)
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.rating),
                                                contentDescription = "rating",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .size(14.dp)
                                            )
                                            Spacer(modifier = Modifier.width(5.dp))
                                            Text(
                                                text = "(${product.data.rating})",
                                                fontSize = 13.sp,
                                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                            )
                                            Spacer(modifier = Modifier.weight(1f))
                                            Box (
                                                modifier = Modifier
                                                    .border(
                                                        width = 1.dp,
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                                            alpha = 0.5f
                                                        ),
                                                        shape = RoundedCornerShape(10.dp)
                                                    )
                                            ) {
                                                Row (
                                                    verticalAlignment = Alignment.CenterVertically,
                                                ) {
                                                    IconButton(
                                                        onClick = {
                                                            if (quantity > 0) {
                                                                setQuantity(quantity - 1)
                                                            }
                                                        },
                                                        modifier = Modifier.height(15.dp)
                                                    ) {
                                                        Icon(
                                                            painter = painterResource(id = R.drawable.remove),
                                                            modifier = Modifier
                                                                .size(12.dp)
                                                                .clip(MaterialTheme.shapes.medium),
                                                            contentDescription = "remove"
                                                        )
                                                    }
                                                    Text(text = quantity.toString(), color = MaterialTheme.colorScheme.onTertiaryContainer)
                                                    IconButton(
                                                        onClick = {
                                                            setQuantity(quantity + 1)
                                                        },
                                                        modifier = Modifier.height(15.dp)
                                                    ) {
                                                        Icon(
                                                            painter = painterResource(id = R.drawable.add),
                                                            modifier = Modifier
                                                                .size(12.dp)
                                                                .clip(MaterialTheme.shapes.medium),
                                                            contentDescription = "add"
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                            ) {
                                Text(
                                    text = "Payment Method",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    modifier = Modifier.padding(start = 5.dp, bottom = 5.dp)
                                )
                                listOf(
                                    "BRI Virtual Account" to R.drawable.baseline_credit_card_24,
                                    "BCA Virtual Account" to R.drawable.baseline_credit_card_24,
                                    "Cash On Delivery (COD)" to R.drawable.baseline_delivery_dining_24
                                ).forEach { (method, icon) ->
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(
                                                width = 1.dp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                                    alpha = 0.5f
                                                ),
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                            .clickable { setSelectedPaymentMethod(method) }
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(10.dp)
                                        ) {
                                            Image(
                                                painter = painterResource(id = icon),
                                                contentDescription = method,
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .size(23.dp)
                                                    .clip(MaterialTheme.shapes.medium)
                                            )
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Text(
                                                text = method,
                                                style = MaterialTheme.typography.titleMedium,
                                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                            )
                                            Spacer(modifier = Modifier.weight(1f))
                                            Image(
                                                painter = painterResource(
                                                    id = if (selectedPaymentMethod == method) {
                                                        R.drawable.baseline_radio_button_checked_24
                                                    } else {
                                                        R.drawable.baseline_radio_button_unchecked_24
                                                    }
                                                ),
                                                contentDescription = "radiobutton",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .size(18.dp)
                                                    .clip(MaterialTheme.shapes.medium)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                }
                            }
                        }
                    }

                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp)
                    ){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(15.dp)
                        ) {
                            Text(
                                text = "Payment Details",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                modifier = Modifier.padding(start = 10.dp, bottom = 5.dp)
                            )
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, end = 10.dp)
                            ) {
                                Text(
                                    text = "Total Price (${quantity} items)",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "Rp ${formattedPrice(product.data.price * quantity)}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, end = 10.dp)
                            ) {
                                Text(
                                    text = "Shipping Fee",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "Rp ${formattedPrice(shippingFee)}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, end = 10.dp)
                            ) {
                                Text(
                                    text = "Service Fee",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "Rp ${
                                        if(selectedPaymentMethod == "Cash On Delivery (COD)") {
                                            formattedPrice(2500)
                                        } else {
                                            formattedPrice(5000)
                                        }
                                    }",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                            }
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp, horizontal = 10.dp),
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                                thickness = 1.dp
                            )
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, end = 10.dp)
                            ) {
                                Text(
                                    text = "Total Payment",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "Rp ${
                                        formattedPrice(product.data.price * quantity + shippingFee +
                                                if(selectedPaymentMethod == "Cash On Delivery (COD)") {
                                                    2500
                                                } else {
                                                    5000
                                                })
                                    }",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                )
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    ErrorMessage(message = product.errorMessage)
                }
                else -> {}
            }
        }
    }
}