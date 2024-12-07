package com.bangkit.capstone.agreaseapp.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")

    data object Transaction : Screen("transaction")

    // chatBot
    data object Chat : Screen("chat")

    // Account
    data object Profile : Screen("profile")
    data object MyAccount : Screen("myaccount")
    data object ChangePassword : Screen("changepassword")

    // Auth
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Welcome : Screen("welcome")
}