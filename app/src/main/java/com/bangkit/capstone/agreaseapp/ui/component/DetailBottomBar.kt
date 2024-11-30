package com.bangkit.capstone.agreaseapp.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bangkit.capstone.agreaseapp.R

@Composable
fun DetailBottomBar(
    modifier: Modifier = Modifier,
    onBuyClicked: () -> Unit,
    onSaveClicked: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Button(
            onClick = onBuyClicked,
            modifier = modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text(
                text = "Buy Now!",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
        }
        Button(
            onClick = onSaveClicked,
            modifier = modifier
        ) {
            Icon(painter = painterResource(id = R.drawable.baseline_favorite_border_24), contentDescription = "Favorite" )
        }
    }
}