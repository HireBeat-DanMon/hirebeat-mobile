package com.hirebeat.com.app.danmon.feature.gig_requests.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hirebeat.com.app.danmon.core.theme.Sizing
import com.hirebeat.com.app.danmon.core.theme.Spacing
import com.hirebeat.com.app.danmon.feature.gig_requests.domain.entities.GigRequestItem

@Composable
fun GigRequestCard(
    request: GigRequestItem,
    isMusician: Boolean,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(Spacing.Medium)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    )
                    Spacer(modifier = Modifier.width(Spacing.Small))
                    Column {
                        Text(
                            text = "Nombre del Usuario",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (isMusician) "Reclutador" else "Músico",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                val (statusColor, statusBg, statusText) = when (request.status) {
                    "ACCEPTED" -> Triple(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primaryContainer, "ACEPTADA")
                    "REJECTED" -> Triple(MaterialTheme.colorScheme.error, MaterialTheme.colorScheme.errorContainer, "RECHAZADA")
                    else -> Triple(MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.secondaryContainer, "PENDIENTE")
                }

                Text(
                    text = statusText,
                    color = statusColor,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 10.sp
                    ),
                    modifier = Modifier
                        .background(statusBg, CircleShape)
                        .padding(horizontal = Spacing.Small, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(Spacing.Medium))

            Surface(
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(Spacing.Medium),
                    verticalArrangement = Arrangement.spacedBy(Spacing.Small)
                ) {
                    EventDetailRow(icon = Icons.Default.CalendarToday, label = "FECHA DE EVENTO", value = request.startTime)
                    EventDetailRow(icon = Icons.Default.Schedule, label = "HORARIO", value = "${request.startTime} - ${request.endTime}")
                    EventDetailRow(icon = Icons.Default.LocationOn, label = "UBICACIÓN", value = request.location)
                }
            }

            if (isMusician && request.status == "PENDING") {
                Spacer(modifier = Modifier.height(Spacing.Medium))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.Small)
                ) {
                    OutlinedButton(
                        onClick = onReject,
                        modifier = Modifier.weight(1f).height(Sizing.ButtonHeight),
                        shape = MaterialTheme.shapes.large,
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.error),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        // Aplicamos labelLarge para aprovechar tus fuentes globales
                        Text("RECHAZAR", style = MaterialTheme.typography.labelLarge)
                    }
                    Button(
                        onClick = onAccept,
                        modifier = Modifier.weight(1f).height(Sizing.ButtonHeight),
                        shape = MaterialTheme.shapes.large,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("ACEPTAR", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
    }
}

@Composable
fun EventDetailRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(16.dp).padding(top = 2.dp)
        )
        Spacer(modifier = Modifier.width(Spacing.Small))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}