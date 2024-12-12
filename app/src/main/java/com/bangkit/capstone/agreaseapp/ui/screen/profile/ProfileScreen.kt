package com.bangkit.capstone.agreaseapp.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bangkit.capstone.agreaseapp.R
import com.bangkit.capstone.agreaseapp.ui.component.respond.ErrorMessage
import com.bangkit.capstone.agreaseapp.ui.component.respond.LoadingIndicator
import com.bangkit.capstone.agreaseapp.ui.screen.ViewModelFactory
import com.bangkit.capstone.agreaseapp.ui.state.UiState

@Composable
fun ProfileScreen(
    redirectToWelcome: (String) -> Unit,
    redirectToMyAccount: () -> Unit,
    redirectToChatbot: () -> Unit,
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
) {
    val role by viewModel.userRole

    LaunchedEffect(key1 = role) {
        viewModel.getUserRole()
    }

    viewModel.user.collectAsState(initial = UiState.Loading).value.let { user ->
        when (user) {
            is UiState.Loading -> {
                LoadingIndicator(modifier = Modifier)
                viewModel.getUser()
            }

            is UiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxSize()
                ) {
                    item {
                        user.data.apply {
                            ElevatedCard(
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 4.dp
                                ),

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        redirectToMyAccount()
                                    }
                            ) {
                                Row (
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.White)
                                        .padding(20.dp)
                                ){
                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = "Profile Image",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .size(60.dp)
                                            .clip(CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Column {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = nama,
                                                style = MaterialTheme.typography.titleMedium,
                                            )
                                            Spacer(modifier = Modifier.width(5.dp))
                                            when(role) {
                                                is UiState.Success -> {
                                                    ElevatedCard(
                                                        elevation = CardDefaults.cardElevation(
                                                            defaultElevation = 15.dp
                                                        ),
                                                        colors = if ((role as UiState.Success<String>).data == "seller") {
                                                            CardDefaults.cardColors(Color(0xFF2C98EE))
                                                        } else {
                                                            CardDefaults.cardColors(Color(0xFF00BF63))
                                                        },
                                                        modifier = Modifier
                                                            .clip(shape = CardDefaults.shape)
                                                            .shadow(
                                                                elevation = 0.dp,
                                                                spotColor = Color.Transparent,
                                                                shape = CardDefaults.shape
                                                            )
                                                    ){
                                                        Text(
                                                            text = if ((role as UiState.Success<String>).data == "seller") {
                                                                "Seller"
                                                            } else {
                                                                "Buyer"
                                                            },
                                                            fontSize = 12.sp,
                                                            color = Color.White,
                                                            modifier = Modifier.padding(vertical = 2.dp, horizontal = 7.dp)
                                                        )
                                                    }
                                                }
                                                else -> { "$nama (Guest)" }
                                            }
                                        }
                                        Text(
                                            text = email,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = Color.Gray
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        user.data.apply {
                            Spacer(modifier = Modifier.height(20.dp))
                            ElevatedCard(
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 6.dp
                                ),

                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Column {
                                    Row (
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.White)
                                            .padding(20.dp)
                                            .clickable {
                                                redirectToMyAccount()
                                            }
                                    ){
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = "My Account",
                                        )
                                        Spacer(modifier = Modifier.width(20.dp))
                                        Text(
                                            text = "My Account",
                                            style = MaterialTheme.typography.titleMedium,
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowRight,
                                            contentDescription = "",
                                        )
                                    }
                                    Divider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.White)
                                            .padding(horizontal = 15.dp),
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                                        thickness = 1.dp
                                    )
                                    when(role){
                                        is UiState.Success -> {
                                            if ((role as UiState.Success<String>).data == "buyer") {
                                                Row (
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .background(Color.White)
                                                        .padding(20.dp)
                                                        .clickable {
                                                            redirectToChatbot()
                                                        }
                                                ){
                                                    Icon(
                                                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_chat_24),
                                                        contentDescription = "Chatbot",
                                                    )
                                                    Spacer(modifier = Modifier.width(20.dp))
                                                    Text(
                                                        text = "Chatbot",
                                                        style = MaterialTheme.typography.titleMedium,
                                                    )
                                                }
                                                Divider(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .background(Color.White)
                                                        .padding(horizontal = 15.dp),
                                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                                                    thickness = 1.dp
                                                )
                                                Row (
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .background(Color.White)
                                                        .padding(20.dp)
                                                        .clickable { }
                                                ){
                                                    Icon(
                                                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_room_preferences_24),
                                                        contentDescription = "Preference",
                                                    )
                                                    Spacer(modifier = Modifier.width(20.dp))
                                                    Text(
                                                        text = "User Preference",
                                                        style = MaterialTheme.typography.titleMedium,
                                                    )
                                                }
                                                Divider(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .background(Color.White)
                                                        .padding(horizontal = 15.dp),
                                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                                                    thickness = 1.dp
                                                )
                                            }
                                        }
                                        else -> {}
                                    }
                                    Row (
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.White)
                                            .padding(20.dp)
                                            .clickable { }
                                    ){
                                        Icon(
                                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_settings_24),
                                            contentDescription = "Settings",
                                        )
                                        Spacer(modifier = Modifier.width(20.dp))
                                        Text(
                                            text = "Settings",
                                            style = MaterialTheme.typography.titleMedium,
                                        )
                                    }
                                    Divider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.White)
                                            .padding(horizontal = 15.dp),
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                                        thickness = 1.dp
                                    )

                                    Row (
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.White)
                                            .padding(20.dp)
                                            .clickable {
                                                viewModel.logout()
                                                redirectToWelcome("")
                                            },
                                    ){
                                        Icon(
                                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_logout),
                                            contentDescription = "Log Out",
                                        )
                                        Spacer(modifier = Modifier.width(20.dp))
                                        Text(
                                            text = "Log Out",
                                            style = MaterialTheme.typography.titleMedium,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            is UiState.Error -> {
                ErrorMessage(message = user.errorMessage, modifier = Modifier)
            }

            is UiState.Unauthorized -> {
                redirectToWelcome("Session is expired")
            }
        }
    }
}