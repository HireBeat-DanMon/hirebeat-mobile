package com.hirebeat.com.app.danmon.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.toRoute
import com.hirebeat.com.app.danmon.feature.auth.presentation.screens.AuthScreen
import com.hirebeat.com.app.danmon.feature.gig_requests.presentation.screens.GigRequestsScreen
import com.hirebeat.com.app.danmon.feature.profile.presentation.screens.FeedScreen
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
        startDestination = startDestination,
    ) {

        composable<AuthRoute> {
            AuthScreen(
                onLoginSuccess = {
                    navController.navigate(FeedRoute) {
                        popUpTo<AuthRoute> { inclusive = true }
                    }
                }
            )
        }

        composable<SaveProfileRoute> {
            SaveProfileScreen(
                onProfileSaved = {
                    navController.navigate(FeedRoute) {
                        popUpTo<SaveProfileRoute> { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable<FeedRoute> {
            FeedScreen(
                onProfileClick = { id ->
                    navController.navigate(ProfileDetailRoute(userId = id))
                }
            )
        }

        composable<GigRequestsRoute> {
            GigRequestsScreen()
        }

        composable<ProfileDetailRoute> { backStackEntry ->
            val route: ProfileDetailRoute = backStackEntry.toRoute()
            ProfileScreen(
                userId = route.userId,
                onBack = { navController.popBackStack() }
            )
        }

        composable<MyProfileRoute> {
            MyProfileScreen(
                onBack = { navController.popBackStack() },
                onEditProfile = { navController.navigate(SaveProfileRoute) },
                onLogout = {
                    navController.navigate(AuthRoute) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}