package com.hirebeat.com.app.danmon.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp // <- Importación de 'dp' agregada
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hirebeat.com.app.danmon.core.navigation.FeedRoute
import com.hirebeat.com.app.danmon.core.navigation.GigRequestsRoute
import com.hirebeat.com.app.danmon.core.navigation.MyProfileRoute // <- Ruta correcta según tu archivo

@Composable
fun HireBeatBottomNavigation(navController: NavHostController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        // --- INICIO / FEED ---
        NavigationBarItem(
            // Al serializar, el nombre de la ruta incluye el nombre de la clase
            selected = currentDestination?.hierarchy?.any { it.route?.contains("FeedRoute") == true } == true,
            onClick = { navigateToTab(navController, FeedRoute) },
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("INICIO") }
        )

        // --- BANDEJA / SOLICITUDES ---
        NavigationBarItem(
            selected = currentDestination?.hierarchy?.any { it.route?.contains("GigRequestsRoute") == true } == true,
            onClick = { navigateToTab(navController, GigRequestsRoute) },
            icon = { Icon(Icons.Default.MailOutline, contentDescription = null) },
            label = { Text("BANDEJA") }
        )

        // --- PERFIL ---
        NavigationBarItem(
            selected = currentDestination?.hierarchy?.any { it.route?.contains("MyProfileRoute") == true } == true,
            onClick = { navigateToTab(navController, MyProfileRoute) }, // Navegación Type-Safe usando el objeto de tu archivo
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            label = { Text("PERFIL") }
        )
    }
}

private fun navigateToTab(navController: NavHostController, route: Any) {
    navController.navigate(route) { // Esta llamada SÍ es completamente type-safe (Navigation 2.8+)
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}