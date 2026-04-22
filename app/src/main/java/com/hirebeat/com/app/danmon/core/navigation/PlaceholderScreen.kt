//package com.hirebeat.com.app.danmon.core.navigation
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import com.hirebeat.com.app.danmon.core.presentation.components.HireBeatButton
//import com.hirebeat.com.app.danmon.core.theme.Spacing
//
//@Composable
//fun PlaceholderScreen(
//    onNavigate: (Any) -> Unit
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(Spacing.Large)
//            .verticalScroll(rememberScrollState()),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(
//            text = "HireBeat: Panel de Pruebas",
//            style = MaterialTheme.typography.headlineSmall,
//            modifier = Modifier.padding(bottom = Spacing.ExtraLarge)
//        )
//
//        Column(verticalArrangement = Arrangement.spacedBy(Spacing.Medium)) {
//            Text("Flujos de Usuario", style = MaterialTheme.typography.labelLarge)
//
//            HireBeatButton(
//                text = "Probar Feed Real",
//                onClick = { onNavigate(FeedRoute) } // <-- Esto ahora sí te llevará al FeedScreen
//            )
//
//            HireBeatButton(
//                text = "Ver Mi Perfil",
//                onClick = { onNavigate(MyProfileRoute) }
//            )
//
//            // --- SECCIÓN PRUEBAS DE EDICIÓN ---
//            Text("Configuración", style = MaterialTheme.typography.labelLarge)
//
//            HireBeatButton(
//                text = "Editar Perfil (SaveProfile)",
//                onClick = { onNavigate(SaveProfileRoute) }
//            )
//
//            // --- SECCIÓN CASOS ESPECÍFICOS ---
//            Text("Casos de Prueba", style = MaterialTheme.typography.labelLarge)
//
//            HireBeatButton(
//                text = "Ver Perfil Externo (ID: 123)",
//                onClick = { onNavigate(ProfileDetailRoute(userId = "123")) }
//            )
//
//            HireBeatButton(
//                text = "Cerrar Sesión / Login",
//                onClick = { onNavigate(AuthRoute) },
//                containerColor = MaterialTheme.colorScheme.error
//            )
//        }
//    }
//}