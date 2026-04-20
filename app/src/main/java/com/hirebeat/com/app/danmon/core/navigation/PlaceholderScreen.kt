package com.hirebeat.com.app.danmon.core.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hirebeat.com.app.danmon.core.presentation.components.HireBeatButton
import com.hirebeat.com.app.danmon.core.theme.Spacing

@Composable
fun PlaceholderScreen(
    onNavigate: (Any) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Spacing.Large),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Menú de Desarrollo (HireBeat)",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = Spacing.ExtraLarge)
        )

        Column(verticalArrangement = Arrangement.spacedBy(Spacing.Medium)) {
            HireBeatButton(
                text = "Ir a Login / Registro",
                onClick = { onNavigate(AuthRoute) }
            )

            HireBeatButton(
                text = "Configurar Mi Perfil",
                onClick = { onNavigate(SaveProfileRoute) }
            )

            HireBeatButton(
                text = "Ver Perfil (Modo Vista)",
                onClick = {
                    onNavigate(ProfileDetailRoute(userId = "test-user-123"))
                }
            )

            HireBeatButton(
                text = "Ver MI Perfil",
                onClick = { onNavigate(MyProfileRoute) } // <-- Botón para tu perfil
            )
        }

        Spacer(modifier = Modifier.height(Spacing.ExtraLarge))

        Text(
            text = "Esta pantalla se reemplazará por el Feed principal próximamente.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}