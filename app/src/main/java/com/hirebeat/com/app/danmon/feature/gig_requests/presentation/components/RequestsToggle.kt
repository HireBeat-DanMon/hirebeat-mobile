package com.hirebeat.com.app.danmon.feature.gig_requests.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.hirebeat.com.app.danmon.core.theme.Sizing
import com.hirebeat.com.app.danmon.core.theme.Spacing

@Composable
fun RequestsToggle(
    isReceivedSelected: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Sizing.ButtonHeight)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(Spacing.ExtraSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(MaterialTheme.shapes.large)
                .background(if (isReceivedSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
                .clickable { if (!isReceivedSelected) onToggle(true) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "RECIBIDAS",
                color = if (isReceivedSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelLarge
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(MaterialTheme.shapes.large)
                .background(if (!isReceivedSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
                .clickable { if (isReceivedSelected) onToggle(false) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "ENVIADAS",
                color = if (!isReceivedSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}