package com.hirebeat.com.app.danmon.feature.gig_requests.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(Spacing.Medium)) {

            // --- Cabecera: Avatar, Nombre, Rol y Estado ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Placeholder para el Avatar
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    )
                    Spacer(modifier = Modifier.width(Spacing.Small))
                    Column {
                        Text(
                            text = "Nombre del Usuario", // Aquí iría el nombre real
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF221A16)
                        )
                        Text(
                            text = if(isMusician) "Reclutador" else "Músico",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }

                // Badge de Estado
                val (statusColor, statusBg, statusText) = when(request.status) {
                    "ACCEPTED" -> Triple(Color(0xFF3B6B61), Color(0xFFE8F0EE), "ACEPTADA")
                    "REJECTED" -> Triple(Color(0xFFB00020), Color(0xFFFDECEA), "RECHAZADA")
                    else -> Triple(Color(0xFFC97E58), Color(0xFFFCEAE3), "PENDIENTE")
                }

                Text(
                    text = statusText,
                    color = statusColor,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 10.sp,
                    modifier = Modifier
                        .background(statusBg, RoundedCornerShape(12.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(Spacing.Medium))

            // --- Caja de Información del Evento ---
            Surface(
                color = Color(0xFFF4EDE8), // Color beige claro de la imagen
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(Spacing.Medium),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    EventDetailRow(icon = Icons.Default.CalendarToday, label = "FECHA DE EVENTO", value = request.startTime) // Formatear fecha después
                    EventDetailRow(icon = Icons.Default.Schedule, label = "HORARIO", value = "${request.startTime} - ${request.endTime}") // Formatear hora después
                    EventDetailRow(icon = Icons.Default.LocationOn, label = "UBICACIÓN", value = request.location)
                }
            }

            // --- Botones de Acción (Solo si está PENDIENTE y es la vista correspondiente) ---
            if (isMusician && request.status == "PENDING") {
                Spacer(modifier = Modifier.height(Spacing.Medium))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.Small)
                ) {
                    OutlinedButton(
                        onClick = onReject,
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFB00020)),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFB00020))
                    ) {
                        Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("RECHAZAR", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                    Button(
                        onClick = onAccept,
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B6B61))
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("ACEPTAR", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun EventDetailRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(icon, contentDescription = null, tint = Color(0xFFC97E58), modifier = Modifier.size(16.dp).padding(top = 2.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            Text(text = value, style = MaterialTheme.typography.bodySmall, color = Color.Black)
        }
    }
}