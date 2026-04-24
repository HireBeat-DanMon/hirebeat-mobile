package com.hirebeat.com.app.danmon.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.hirebeat.com.app.danmon.core.navigation.*

@Composable
fun HireBeatBottomBar(
    currentRoute: Any?,
    roleName: String?,
    onNavigate: (Any) -> Unit,
    onLogout: () -> Unit
) {

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        tonalElevation = 3.dp
    ) {
        NavigationBarItem(
            selected = currentRoute is FeedRoute,
            onClick = { onNavigate(FeedRoute) },
            label = { Text("Explorar") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Explore,
                    contentDescription = "Feed"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        NavigationBarItem(
            selected = false,
            enabled = false,
            onClick = { },
            label = { Text("Suplencias") },
            icon = {
                Icon(
                    Icons.Default.CalendarMonth,
                    contentDescription = null
                )
            }
        )

        if (roleName?.equals("Musician", ignoreCase = true) == true) {
            NavigationBarItem(
                selected = currentRoute is MyProfileRoute,
                onClick = { onNavigate(MyProfileRoute) },
                label = { Text("Mi Perfil") },
                icon = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null
                    )
                }
            )
            NavigationBarItem(
                selected = currentRoute is MyReviewsRoute,
                onClick = { onNavigate(MyReviewsRoute) },
                label = { Text("Reseñas") },
                icon = {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Mis Reseñas"
                    )
                }
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
                    unselectedIconColor = MaterialTheme.colorScheme.error,
                    unselectedTextColor = MaterialTheme.colorScheme.error
                )
            )
        }
    }
}