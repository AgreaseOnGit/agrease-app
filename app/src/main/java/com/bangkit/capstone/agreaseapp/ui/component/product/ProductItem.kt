package com.bangkit.capstone.agreaseapp.ui.component.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bangkit.capstone.agreaseapp.R
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ProductItem(
    id: Int,
    name: String,
    image: String,
    price: Int,
    rating: Double,
    seller: String,
    onNavigateToDetailScreen: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val orientation = LocalConfiguration.current.orientation

    fun formattedPrice(price: Int): String {
        return NumberFormat.getNumberInstance(Locale("id", "ID")).format(price)
    }

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),

        modifier = modifier
            .widthIn(max = screenWidth / 2)
            .padding(
                top = 5.dp,
                bottom = 5.dp,
                start = 5.dp,
                end = 5.dp,
            )
            .clickable {
                onNavigateToDetailScreen(id)
            }
    ) {
        Column(
            modifier = modifier
                .width((screenWidth / 2) - 20.dp)
                .height(275.dp)
                .background(Color.White)
        ) {
            AsyncImage(
                model = image,
                contentDescription = "$name Image",
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .padding(2.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(MaterialTheme.shapes.medium)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, start = 10.dp, bottom = 10.dp, end = 10.dp)
            ) {
                Text(
                    text = name.toUpperCase(Locale.getDefault()),
                    maxLines = 3,
                    lineHeight = 20.sp,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "Rp ${formattedPrice(price)}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                )
                Spacer(modifier = Modifier.weight(1f))
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = seller.toUpperCase(Locale.getDefault()),
                        maxLines = 1,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        modifier = modifier.widthIn(max = screenWidth/4)
                    )
                    Row(
                        modifier = modifier.padding(top = 5.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.rating),
                            contentDescription = "rating",
                            modifier = Modifier
                                .size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = rating.toString(),
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                        )
                    }
                }
            }
        }
    }
}