package com.bangkit.capstone.agreaseapp.ui.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bangkit.capstone.agreaseapp.R
import com.bangkit.capstone.agreaseapp.data.remote.response.RegisterResponse
import com.bangkit.capstone.agreaseapp.ui.screen.ViewModelFactory
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import com.bangkit.capstone.agreaseapp.ui.theme.AgreaseTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyScreen(
    navController: NavHostController = rememberNavController(),
    redirectToLogin: () -> Unit = {},
    viewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
) {
    var codeOTP by remember { mutableStateOf("") }
    val auth: UiState<RegisterResponse> by viewModel.auth
    var verify by remember { mutableStateOf("Verify") }
    var error by remember { mutableStateOf("") }

    DisposableEffect(key1 = auth ){
        when (auth) {
            is UiState.Loading -> {
                verify = "Loading..."
            }
            is UiState.Error -> {
                verify = "Verify"
                error = (auth as UiState.Error).errorMessage
            }
            is UiState.Success -> {
                redirectToLogin()
            }
            else -> {}
        }
        onDispose {  }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Verify User",
                        style = MaterialTheme.typography.titleMedium,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }

            )
        },
    ) { paddingValue ->
        Box(
            modifier = Modifier
                .padding(paddingValue)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(state = rememberScrollState())
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_no_bg),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = error,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Red,
                    textAlign = TextAlign.Center
                )
                ElevatedCard (
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 7.dp
                    ),
                    colors = CardDefaults.cardColors(Color(0xFF00BF63)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = CardDefaults.shape)
                        .shadow(elevation = 0.dp, spotColor = Color.Transparent, shape = CardDefaults.shape)
                        .padding(bottom = 16.dp, top = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                    )
                    {
                        viewModel.verifyData.collectAsState(initial = UiState.Loading).value.let { user ->
                            when (user) {
                                is UiState.Loading -> {
                                    Text(
                                        text = "Loading...",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        textAlign = TextAlign.Center
                                    )
                                    viewModel.checkRegistered()
                                }
                                is UiState.Success -> {
                                    Text(
                                        text = "We have sent a code to your email\n${user.data.authEmail}.\nPlease enter the code to verify your account.",
                                        style = MaterialTheme.typography.titleMedium,
                                        textAlign = TextAlign.Center
                                    )
                                }
                                else -> {}
                            }
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        Box(modifier = Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = codeOTP,
                                onValueChange = { codeOTP = it },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                trailingIcon = { Icon(Icons.Filled.MailOutline, contentDescription = null) },
                                shape = RoundedCornerShape(7.dp),
                                placeholder = { Text("Your Code OTP", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant) },
                                colors = TextFieldDefaults.textFieldColors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(55.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = {
                                if (verify != "Loading...") viewModel.verify( codeOTP = if (codeOTP.isNotEmpty()) codeOTP.toInt() else 0)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF0E7B75),
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(45.dp)
                        ) {
                            Text(
                                verify,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Spacer(modifier = Modifier.height(30.dp))

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Your email in Incorrect?",
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "Register",
                                style = MaterialTheme
                                    .typography.titleMedium
                                    .copy(textDecoration = TextDecoration.Underline),
                                modifier = Modifier
                                    .clickable {
                                        viewModel.destroyVerifyUID()
                                        navController.popBackStack()
                                    }
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun VerifyScreenPreview() {
    AgreaseTheme {
        VerifyScreen()
    }
}