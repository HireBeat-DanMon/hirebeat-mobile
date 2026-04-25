package com.hirebeat.com.app.danmon.feature.gig_requests.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hirebeat.com.app.danmon.core.theme.Sizing
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
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surfaceContainerHigh
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
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )

                IconButton(
                    onClick = onClose,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Cerrar")
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
                    modifier = Modifier
                        .weight(1f)
                        .height(Sizing.ButtonHeight),
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                ) {
                    Text(
                        text = "CANCELAR",
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Button(
                    onClick = onSubmit,
                    enabled = !state.isLoading,
                    modifier = Modifier
                        .weight(1f)
                        .height(Sizing.ButtonHeight),
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = if (state.isLoading) "ENVIANDO..." else "ENVIAR\nSOLICITUD",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}