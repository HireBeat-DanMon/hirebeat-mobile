package com.hirebeat.com.app.danmon.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import com.hirebeat.com.app.danmon.feature.auth.presentation.screens.AuthScreen
import com.hirebeat.com.app.danmon.feature.profile.presentation.screens.MyProfileScreen
import com.hirebeat.com.app.danmon.feature.profile.presentation.screens.SaveProfileScreen
import com.hirebeat.com.app.danmon.feature.profile.presentation.screens.ProfileScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: Any
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<AuthRoute> {
            AuthScreen(
                onLoginSuccess = { destination ->
                    navController.navigate(destination) {
                        popUpTo<AuthRoute> { inclusive = true }
                    }
                }
            )
        }

        composable<SaveProfileRoute> {
            SaveProfileScreen(
                onProfileSaved = {
                    navController.navigate(HomeRoute) {
                        popUpTo<SaveProfileRoute> { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable<HomeRoute> {
            PlaceholderScreen(
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable<ProfileDetailRoute> {
            ProfileScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable<MyProfileRoute> {
            MyProfileScreen(
                onBack = { navController.popBackStack() },
                onEditProfile = { navController.navigate(SaveProfileRoute) },
                onLogout = {
                    navController.navigate(AuthRoute) {
                        popUpTo<HomeRoute> { inclusive = true }
                    }
                }
            )
        }
    }
}