package com.bangkit.capstone.agreaseapp.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")

    // Analysis
    data object DetailAnalysis : Screen("analysis")
    data object Transaction : Screen("transaction")

    // Schedule
    data object Schedule : Screen("schedule")
    data object AddSchedule : Screen("addschedule")

    // Consult
    data object Consult : Screen("consult")

    // Article
    data object DetailArticle : Screen("detail/{id}") {
        fun createRoute(id: Int) = "detail/$id"
    }

    // Account
    data object Profile : Screen("profile")
    data object MyAccount : Screen("myaccount")
    data object ChangePassword : Screen("changepassword")

    // Auth
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Welcome : Screen("welcome")
}