package com.bangkit.capstone.agreaseapp.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bangkit.capstone.agreaseapp.R
import com.bangkit.capstone.agreaseapp.ui.navigation.NavigationItem
import com.bangkit.capstone.agreaseapp.ui.navigation.Screen

@Composable
fun BottomBar(navController: NavHostController, modifier: Modifier = Modifier) {
    NavigationBar(
        tonalElevation = 50.dp,
        containerColor = Color(android.graphics.Color.parseColor("#f2f2f2")),
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.home_nav),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.search_nav),
                icon = ImageVector.vectorResource(id = R.drawable.round_search_24),
                screen = Screen.Search
            ),
            NavigationItem(
                title = stringResource(R.string.transaction_nav),
                icon = ImageVector.vectorResource(id = R.drawable.baseline_list),
                screen = Screen.Transaction
            ),
            NavigationItem(
                title = stringResource(R.string.profile_nav),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),
        )
        navigationItems.map { item ->
            var isSelected: Boolean = currentRoute == item.screen.route
            if (item.screen.route == Screen.Profile.route && currentRoute == Screen.MyAccount.route) isSelected = true

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = isSelected,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
            )
        }
    }
}