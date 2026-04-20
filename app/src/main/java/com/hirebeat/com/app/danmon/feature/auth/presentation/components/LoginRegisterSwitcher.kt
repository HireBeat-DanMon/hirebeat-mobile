package com.hirebeat.com.app.danmon.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.hirebeat.com.app.danmon.core.theme.*

@Composable
fun LoginRegisterSwitcher(
    isLoginMode: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Sizing.ButtonCorner))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(Spacing.ExtraSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(Sizing.ButtonCorner))
                .background(if (isLoginMode) MaterialTheme.colorScheme.primary else Color.Transparent)
                .clickable { if (!isLoginMode) onToggle() }
                .padding(vertical = Spacing.Small),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "INICIAR SESIÓN",
                color = if (isLoginMode) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(Sizing.ButtonCorner))
                .background(if (!isLoginMode) MaterialTheme.colorScheme.primary else Color.Transparent)
                .clickable { if (isLoginMode) onToggle() }
                .padding(vertical = Spacing.Small),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "REGISTRARSE",
                color = if (!isLoginMode) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}