package com.bangkit.capstone.agreaseapp.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")

    data object Transaction : Screen("transaction")
    data object Search : Screen("search")

    // Account
    data object Profile : Screen("profile")
    data object MyAccount : Screen("myaccount")

    // Auth
    data object Login : Screen("login")
    data object BuyerRegister : Screen("buyer_register")
    data object SellerRegister : Screen("seller_register")
    data object Verify : Screen("verify")
    data object Welcome : Screen("welcome")
}