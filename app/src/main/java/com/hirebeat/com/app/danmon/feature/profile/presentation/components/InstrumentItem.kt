package com.hirebeat.com.app.danmon.feature.profile.presentation.components

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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hirebeat.com.app.danmon.core.theme.*
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.*

@Composable
fun InstrumentItem(
    profileInstrument: ProfileInstrument,
    onLevelChange: (Int) -> Unit,
    onTogglePrincipal: () -> Unit,
    modifier: Modifier = Modifier
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
            .pointerInput(profileInstrument.instrument.id) {
                detectTapGestures(
                    onDoubleTap = { onTogglePrincipal() }
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = if (profileInstrument.isPrincipal)
                MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            if (profileInstrument.isPrincipal) 2.dp else 1.dp,
            if (profileInstrument.isPrincipal) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.outlineVariant
        ),
        shape = RoundedCornerShape(Sizing.CardCorner),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (profileInstrument.isPrincipal) 4.dp else 0.dp
        )
    ) {
        Column(modifier = Modifier.padding(Spacing.Medium)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = profileInstrument.instrument.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (profileInstrument.isPrincipal)
                        MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onSurface
                )
                if (profileInstrument.isPrincipal) {
                    Icon(
                        imageVector = Icons.Default.Stars,
                        contentDescription = "Instrumento Principal",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Text(
                text = profileInstrument.level,
                style = MaterialTheme.typography.labelMedium,
                color = if (profileInstrument.isPrincipal)
                    MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
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
                            contentDescription = "Nivel $star",
                            tint = if (star <= levelValue) GoldStar
                            else MaterialTheme.colorScheme.outlineVariant,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }
    }
}




