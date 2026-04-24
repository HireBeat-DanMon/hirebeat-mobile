package com.hirebeat.com.app.danmon.feature.gig_requests.presentation.components

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
import androidx.compose.ui.unit.dp
import com.hirebeat.com.app.danmon.core.theme.Sizing
import com.hirebeat.com.app.danmon.core.theme.Spacing

@Composable
fun RequestsToggle(
    isReceivedSelected: Boolean,
    onToggle: (Boolean) -> Unit
) {
    val greenColor = Color(0xFF3B6B61)
    val brownColor = Color(0xFF8D4E2C)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(28.dp)) // Forma de píldora
            .background(Color.White)
            .padding(4.dp), // Pequeño padding interno
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(24.dp))
                .background(if (isReceivedSelected) greenColor else Color.Transparent)
                .clickable { if (!isReceivedSelected) onToggle(true) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "RECIBIDAS",
                color = if (isReceivedSelected) Color.White else brownColor.copy(alpha = 0.7f),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(24.dp))
                .background(if (!isReceivedSelected) greenColor else Color.Transparent)
                .clickable { if (isReceivedSelected) onToggle(false) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "ENVIADAS",
                color = if (!isReceivedSelected) Color.White else brownColor.copy(alpha = 0.7f),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}