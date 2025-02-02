package com.bangkit.capstone.agreaseapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bangkit.capstone.agreaseapp.ui.component.BottomBar
import com.bangkit.capstone.agreaseapp.ui.component.SellerBottomBar
import com.bangkit.capstone.agreaseapp.ui.navigation.Screen
import com.bangkit.capstone.agreaseapp.ui.screen.ViewModelFactory
import com.bangkit.capstone.agreaseapp.ui.screen.auth.BuyerRegisterScreen
import com.bangkit.capstone.agreaseapp.ui.screen.auth.LoginScreen
import com.bangkit.capstone.agreaseapp.ui.screen.auth.SellerRegisterScreen
import com.bangkit.capstone.agreaseapp.ui.screen.auth.VerifyScreen
import com.bangkit.capstone.agreaseapp.ui.screen.auth.WelcomeScreen
import com.bangkit.capstone.agreaseapp.ui.screen.chat.ChatScreen
import com.bangkit.capstone.agreaseapp.ui.screen.home.HomeScreen
import com.bangkit.capstone.agreaseapp.ui.screen.profile.ProfileScreen
import com.bangkit.capstone.agreaseapp.ui.screen.profile.account.MyAccountScreen
import com.bangkit.capstone.agreaseapp.ui.screen.search.SearchScreen
import com.bangkit.capstone.agreaseapp.ui.screen.splash.SplashScreen
import com.bangkit.capstone.agreaseapp.ui.screen.transaction.TransactionScreen
import com.bangkit.capstone.agreaseapp.ui.state.UiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgreaseApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: AppViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var message by remember { mutableStateOf("") }
    val redirectToWelcome = { messageParam: String ->
        message = messageParam
        navController.navigate(Screen.Welcome.route) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }

    val checkVerified by viewModel.isVerified
    val role by viewModel.userRole

    LaunchedEffect(key1 = checkVerified) {
        viewModel.checkVerified()
    }

    when (checkVerified) {
        is UiState.Loading -> {
            SplashScreen(
                onTimeout = {
                    navController.navigate(if ((checkVerified as UiState.Success<Boolean>).data) {
                        Screen.Home.route
                    } else {
                        Screen.Welcome.route
                    }) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            )
        }

        is UiState.Success -> {
            viewModel.getUserRole()
            Scaffold(
                topBar = {
                    if (currentRoute == Screen.MyAccount.route || currentRoute == Screen.Chat.route) {
                        TopAppBar(
                            title = {
                                Text(
                                    text = if (currentRoute == Screen.MyAccount.route) "Detail Account" else "Agrease ChatBot",
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
                    }
                },
                bottomBar =
                {
                    if (currentRoute != Screen.Login.route && currentRoute != Screen.Welcome.route && currentRoute != Screen.BuyerRegister.route && currentRoute != Screen.SellerRegister.route  && currentRoute!= Screen.MyAccount.route  && currentRoute!= Screen.Chat.route && currentRoute != null) {
                        when(role) {
                            is UiState.Success -> {
                                if ((role as UiState.Success<String>).data == "seller") {
                                    SellerBottomBar(navController = navController)
                                }
                                if((role as UiState.Success<String>).data == "buyer") {
                                    BottomBar(navController = navController)
                                }
                            }
                            else -> {}
                        }
                    }
                },
                modifier = modifier
            )
            { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = if ((checkVerified as UiState.Success<Boolean>).data) {
                        Screen.Home.route
                    } else {
                        Screen.Welcome.route
                    },
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(Screen.Home.route) {
                        HomeScreen(
                            redirectToWelcome = { redirectToWelcome("Session is expired") },
                        )
                    }
                    composable(Screen.Welcome.route) {
                        WelcomeScreen(navController = navController, message = message)
                    }
                    composable(Screen.Login.route) {
                        LoginScreen(navController = navController, redirectToHome = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(navController.graph.id) {
                                    inclusive = true
                                }
                            }
                        })
                    }
                    composable(Screen.BuyerRegister.route) {
                        BuyerRegisterScreen(navController = navController, redirectToVerify = {
                            navController.navigate(Screen.Verify.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                restoreState = true
                                launchSingleTop = true
                            }
                        })
                    }
                    composable(Screen.SellerRegister.route) {
                        SellerRegisterScreen(navController = navController, redirectToVerify = {
                            navController.navigate(Screen.Verify.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                restoreState = true
                                launchSingleTop = true
                            }
                        })
                    }
                    composable(Screen.Verify.route) {
                        VerifyScreen(navController = navController, redirectToLogin = {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                restoreState = true
                                launchSingleTop = true
                            }
                        })
                    }
                    composable(Screen.Search.route) {
                        SearchScreen(
                            redirectToWelcome = { redirectToWelcome("Session is expired") }
                        )
                    }
                    composable(Screen.Transaction.route) {
                        TransactionScreen(
                            redirectToWelcome = { redirectToWelcome("Session is expired") }
                        )
                    }
                    composable(Screen.Chat.route) {
                        ChatScreen(
                            redirectToWelcome = { redirectToWelcome("") },
                        )
                    }
                    composable(Screen.Profile.route) {
                        ProfileScreen(
                            redirectToWelcome = { redirectToWelcome("") },
                            redirectToMyAccount = { navController.navigate(Screen.MyAccount.route) },
                            redirectToChatbot = { navController.navigate(Screen.Chat.route) },
                        )
                    }
                    composable(Screen.MyAccount.route) {
                        MyAccountScreen(
                            redirectToWelcome = { redirectToWelcome("Session is expired") },
                        )
                    }
                }
            }
        }

        else -> {}
    }
}