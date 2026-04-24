package com.hirebeat.com.app.danmon.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hirebeat.com.app.danmon.core.navigation.*

@Composable
fun HireBeatBottomNavigation(
    navController: NavHostController,
    roleName: String?,
    onLogout: () -> Unit
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        tonalElevation = 3.dp
    ) {
        val isFeedSelected = currentDestination?.hierarchy?.any { it.route?.contains("FeedRoute") == true } == true
        NavigationBarItem(
            selected = isFeedSelected,
            onClick = { navigateToTab(navController, FeedRoute) },
            label = { Text("Explorar") },
            icon = { Icon(Icons.Default.Explore, contentDescription = "Feed") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            )
        )

        val isGigSelected = currentDestination?.hierarchy?.any { it.route?.contains("GigRequestsRoute") == true } == true
        NavigationBarItem(
            selected = isGigSelected,
            onClick = { navigateToTab(navController, GigRequestsRoute) },
            label = { Text("Suplencias") },
            icon = { Icon(Icons.Default.CalendarMonth, contentDescription = null) }
        )

        if (roleName?.contains("Musician", ignoreCase = true) == true) {
            val isProfileSelected = currentDestination?.hierarchy?.any { it.route?.contains("MyProfileRoute") == true } == true
            NavigationBarItem(
                selected = isProfileSelected,
                onClick = { navigateToTab(navController, MyProfileRoute) },
                label = { Text("Mi Perfil") },
                icon = { Icon(Icons.Default.Person, contentDescription = null) }
            )

            val isReviewsSelected = currentDestination?.hierarchy?.any { it.route?.contains("MyReviewsRoute") == true } == true
            NavigationBarItem(
                selected = isReviewsSelected,
                onClick = { navigateToTab(navController, MyReviewsRoute) },
                label = { Text("Reseñas") },
                icon = { Icon(Icons.Default.Star, contentDescription = "Mis Reseñas") }
            )
        } else {

            NavigationBarItem(
                selected = false,
                onClick = onLogout,
                label = { Text("Salir") },
                icon = {
                    Icon(Icons.Default.Logout, contentDescription = "Salir", tint = MaterialTheme.colorScheme.error)
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedTextColor = MaterialTheme.colorScheme.error
                )
            )
        }
    }
}

private fun navigateToTab(navController: NavHostController, route: Any) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}