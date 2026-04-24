package com.hirebeat.com.app.danmon.feature.gig_requests.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hirebeat.com.app.danmon.core.theme.Spacing
import com.hirebeat.com.app.danmon.feature.gig_requests.presentation.components.*

@Composable
fun GigRequestFormScreen(
    state: GigRequestFormState,
    onClose: () -> Unit,
    onDateClick: () -> Unit,
    onStartTimeClick: () -> Unit,
    onEndTimeClick: () -> Unit,
    onLocationChange: (String) -> Unit,
    onPaymentChange: (String) -> Unit,
    onDetailsChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(
            topStart = 32.dp,
            topEnd = 32.dp,
            bottomStart = 32.dp,
            bottomEnd = 32.dp
        ),
        color = Color(0xFFF4EDE8)
    ) {
        Column(
            modifier = Modifier.padding(Spacing.Large),
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Solicitar Suplencia",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8D4E2C)
                )
                IconButton(
                    onClick = onClose,
                    modifier = Modifier.background(Color.White, RoundedCornerShape(12.dp))
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(Spacing.Small))

            GigClickableField(
                label = "Fecha del Evento",
                value = state.selectedDate?.toString() ?: "",
                icon = Icons.Default.CalendarToday,
                onClick = onDateClick
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.Medium)
            ) {
                GigClickableField(
                    label = "Inicio",
                    value = state.startTime?.toString() ?: "",
                    icon = Icons.Default.Schedule,
                    onClick = onStartTimeClick,
                    modifier = Modifier.weight(1f)
                )

                GigClickableField(
                    label = "Fin (Aprox)",
                    value = state.endTime?.toString() ?: "",
                    icon = Icons.Default.Schedule,
                    onClick = onEndTimeClick,
                    modifier = Modifier.weight(1f)
                )
            }

            GigTextField(
                label = "Lugar / Ubicación",
                value = state.location,
                onValueChange = onLocationChange,
                icon = Icons.Default.LocationOn,
                placeholder = "Ej. Salón de Eventos Las Palm..."
            )

            GigTextField(
                label = "Pago Ofrecido (MXN)",
                value = state.paymentOffered,
                onValueChange = onPaymentChange,
                icon = Icons.Default.AttachMoney,
                placeholder = "Ej. 1500",
                keyboardType = KeyboardType.Number
            )

            GigTextField(
                label = "Detalles Adicionales",
                value = state.additionalDetails,
                onValueChange = onDetailsChange,
                icon = Icons.Default.Description,
                placeholder = "Vestimenta requerida, repertorio sugerido, etc...",
                singleLine = false,
                minLines = 3
            )

            Spacer(modifier = Modifier.height(Spacing.Medium))

            if (state.error != null) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.Medium)
            ) {
                Button(
                    onClick = onClose,
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0DFD8)) // Beige oscuro
                ) {
                    Text(
                        "CANCELAR",
                        color = Color(0xFF8D4E2C),
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                Button(
                    onClick = onSubmit,
                    enabled = !state.isLoading,
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B6B61)) // Verde oscuro de la imagen
                ) {
                    Text(
                        if (state.isLoading) "ENVIANDO..." else "ENVIAR\nSOLICITUD",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        fontSize = 12.sp,
                        lineHeight = 14.sp
                    )
                }
            }
        }
    }
}