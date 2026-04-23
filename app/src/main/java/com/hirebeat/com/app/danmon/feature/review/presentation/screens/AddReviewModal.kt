package com.hirebeat.com.app.danmon.feature.review.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hirebeat.com.app.danmon.core.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReviewModal(
    onDismiss: () -> Unit,
    onSubmit: (rating: Int, comment: String) -> Unit,
    isSubmitting: Boolean
) {
    var rating by remember { mutableIntStateOf(0) }
    var comment by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFFFAF6F3), // Color crema del fondo de la imagen
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.Large)
        ) {
            // Header del Modal
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Escribir Reseña",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF8D4E2C), // Color primary
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color(0xFFEFE8E3), RoundedCornerShape(8.dp))
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = Color(0xFF8D4E2C))
                }
            }

            Spacer(modifier = Modifier.height(Spacing.Large))

            // Estrellas
            Text(
                text = "CALIFICACIÓN",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF8D4E2C),
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.padding(vertical = Spacing.Small),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                (1..5).forEach { star ->
                    Icon(
                        imageVector = if (star <= rating) Icons.Default.Star else Icons.Default.StarBorder,
                        contentDescription = null,
                        tint = if (star <= rating) Color(0xFFFFB800) else Color.LightGray,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { rating = star }
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.Medium))

            // Comentario
            Text(
                text = "DETALLES DE LA EXPERIENCIA",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF8D4E2C),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(Spacing.ExtraSmall))
            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                placeholder = { Text("Ej. Excelente músico, muy puntual...", color = Color.Gray) },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color(0xFF8D4E2C)
                )
            )

            Spacer(modifier = Modifier.height(Spacing.Large))

            // Botones (Estilo de la imagen)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.Medium)
            ) {
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF0EAE5),
                        contentColor = Color(0xFF8D4E2C)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("CANCELAR", fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { onSubmit(rating, comment) },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    enabled = !isSubmitting,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3B5D55), // Verde oscuro de la imagen
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(if (isSubmitting) "ENVIANDO..." else "ENVIAR", fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(Spacing.Large))
        }
    }
}