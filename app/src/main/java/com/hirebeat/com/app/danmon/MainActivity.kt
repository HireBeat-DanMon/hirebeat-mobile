package com.hirebeat.com.app.danmon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hirebeat.com.app.danmon.core.data.SessionManager
import com.hirebeat.com.app.danmon.core.navigation.*
import com.hirebeat.com.app.danmon.core.presentation.components.HireBeatBottomBar
import com.hirebeat.com.app.danmon.core.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var startDestination by remember { mutableStateOf<Any?>(null) }

            LaunchedEffect(Unit) {
                val token = sessionManager.authToken.firstOrNull()
                startDestination = if (!token.isNullOrEmpty()) FeedRoute else AuthRoute
            }

            val destination = startDestination ?: return@setContent

            AppTheme(dynamicColor = false) {
                val navController = rememberNavController()
                val roleName by sessionManager.userRoleName.collectAsState(initial = null)
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    bottomBar = {
                        val isAuthScreen = currentRoute?.contains("AuthRoute") == true

                        if (!isAuthScreen) {
                            HireBeatBottomBar(
                                currentRoute = currentRoute,
                                roleName = roleName,
                                onNavigate = { route ->
                                    navController.navigate(route) {
                                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                onLogout = {
                                    navController.navigate(AuthRoute) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavGraph(
                            navController = navController,
                            startDestination = destination
                        )
                    }
                }
            }
        }
    }
}