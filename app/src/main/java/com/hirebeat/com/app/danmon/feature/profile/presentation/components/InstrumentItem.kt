package com.hirebeat.com.app.danmon.feature.profile.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hirebeat.com.app.danmon.core.theme.*
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.*

@Composable
fun InstrumentItem(
    profileInstrument: ProfileInstrument,
    onLevelChange: (Int) -> Unit,
    onTogglePrincipal: () -> Unit
) {
    val levelValue = when (profileInstrument.level.uppercase()) {
        "BASICO" -> 1
        "PRINCIPIANTE" -> 2
        "INTERMEDIO" -> 3
        "AVANZADO" -> 4
        "PROFESIONAL" -> 5
        else -> 1
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            // El truco es poner el detectTapGestures ANTES que cualquier otra cosa
            .pointerInput(profileInstrument.instrument.id) {
                detectTapGestures(
                    onDoubleTap = { onTogglePrincipal() }
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = if (profileInstrument.isPrincipal)
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
            else Color.White
        ),
        border = BorderStroke(
            if (profileInstrument.isPrincipal) 2.dp else 1.dp,
            if (profileInstrument.isPrincipal) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.outlineVariant
        ),
        shape = RoundedCornerShape(Sizing.CardCorner)
    ) {
        Column(modifier = Modifier.padding(Spacing.Medium)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = profileInstrument.instrument.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                if (profileInstrument.isPrincipal) {
                    Icon(
                        imageVector = Icons.Default.Stars, // Usamos Stars para que sea diferente a las de nivel
                        contentDescription = "Principal",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Text(
                text = profileInstrument.level,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )

            Row(modifier = Modifier.padding(top = 4.dp)) {
                (1..5).forEach { star ->
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { onLevelChange(star) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (star <= levelValue)
                                Icons.Default.Star
                            else Icons.Default.StarBorder,
                            contentDescription = null,
                            tint = if (star <= levelValue)
                                Color(0xFFFFB800)
                            else Color.LightGray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}




