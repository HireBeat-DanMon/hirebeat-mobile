package com.hirebeat.com.app.danmon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.hirebeat.com.app.danmon.core.data.SessionManager
import com.hirebeat.com.app.danmon.core.navigation.*
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
                startDestination = if (!token.isNullOrEmpty()) HomeRoute else AuthRoute
            }

            AppTheme(dynamicColor = false) {
                val navController = rememberNavController()

                if (startDestination != null) {
                    NavGraph(
                        navController = navController,
                        startDestination = startDestination!!
                    )
                }
            }
        }
    }
}