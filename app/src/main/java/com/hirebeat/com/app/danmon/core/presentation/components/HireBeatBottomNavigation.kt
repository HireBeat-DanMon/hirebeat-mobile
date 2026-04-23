package com.hirebeat.com.app.danmon.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.hirebeat.com.app.danmon.core.navigation.*
import com.hirebeat.com.app.danmon.core.theme.Spacing

@Composable
fun HireBeatBottomBar(
    currentRoute: Any?,
    roleName: String?,
    onNavigate: (Any) -> Unit,
    onLogout: () -> Unit
) {

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = Spacing.ExtraSmall
    ) {
        NavigationBarItem(
            selected = currentRoute is FeedRoute,
            onClick = { onNavigate(FeedRoute) },
            label = { Text("Explorar") },
            icon = { Icon(Icons.Default.Explore, contentDescription = "Feed") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            )
        )

        NavigationBarItem(
            selected = false,
            enabled = false,
            onClick = { },
            label = { Text("Suplencias") },
            icon = { Icon(Icons.Default.CalendarMonth, contentDescription = null) }
        )

        if (roleName?.equals("Musician", ignoreCase = true) == true) {
            NavigationBarItem(
                selected = currentRoute is MyProfileRoute,
                onClick = { onNavigate(MyProfileRoute) },
                label = { Text("Mi Perfil") },
                icon = { Icon(Icons.Default.Person, contentDescription = null) }
            )
            NavigationBarItem(
                selected = currentRoute is MyReviewsRoute,
                onClick = { onNavigate(MyReviewsRoute) },
                label = { Text("Mis Reseñas") },
                icon = { Icon(Icons.Default.Star, contentDescription = "Mis Reseñas") }
            )
        } else {
            NavigationBarItem(
                selected = false,
                onClick = onLogout,
                label = { Text("Salir") },
                icon = {
                    Icon(
                        Icons.Default.Logout,
                        contentDescription = "Cerrar Sesión",
                        tint = MaterialTheme.colorScheme.error
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedTextColor = MaterialTheme.colorScheme.error
                )
            )
        }
    }
}