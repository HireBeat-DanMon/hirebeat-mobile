package com.hirebeat.com.app.danmon.feature.profile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hirebeat.com.app.danmon.core.theme.Sizing
import com.hirebeat.com.app.danmon.core.theme.Spacing
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.UserProfile

@Composable
fun ProfileCard(
    profile: UserProfile,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Obtenemos el instrumento principal para mostrar su nivel
    val mainInstrument = profile.instruments.find { it.isPrincipal } ?: profile.instruments.firstOrNull()

    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.Medium, vertical = Spacing.Small),
        shape = RoundedCornerShape(Sizing.CardCorner),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(Spacing.Medium)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // Imagen con indicador de estado (punto verde)
                Box(contentAlignment = Alignment.BottomEnd) {
                    AsyncImage(
                        model = profile.photoUrl, // Aquí iría profile.photoUrl si existiera en el dominio
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(Spacing.Medium))
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentScale = ContentScale.Crop
                    )
                    // Indicador online (punto verde)
                    Surface(
                        modifier = Modifier.size(16.dp).offset(x = 4.dp, y = 4.dp),
                        shape = CircleShape,
                        color = Color(0xFF4CAF50),
                        border = androidx.compose.foundation.BorderStroke(2.dp, Color.White)
                    ) {}
                }

                Spacer(modifier = Modifier.width(Spacing.Medium))

                // Información central
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = profile.fullName,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Icon(Icons.Default.MoreVert, contentDescription = null, tint = Color.Gray)
                    }

                    // Instrumento y Nivel
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.PlayCircle,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = mainInstrument?.instrument?.name ?: "Músico",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    // Nivel de habilidad (Ej: Profesional)
                    Text(
                        text = mainInstrument?.level ?: "Básico",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(Spacing.ExtraSmall))

                    // Ciudad
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = Color(0xFFC97E58)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = profile.city,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(Spacing.Medium))

            // Chips de Géneros (Máximo 2 o 3 para no saturar)
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.Small)) {
                profile.genres.take(2).forEach { genre ->
                    Surface(
                        color = Color(0xFFF6E5DE), // Tono crema de la imagen
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = genre.name.uppercase(),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF8D4E2C)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(Spacing.Medium))

            // Botón inferior "Ver Disponibilidad"
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(Sizing.CardCorner),
                color = MaterialTheme.colorScheme.surfaceContainerLow, // Fondo suave
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray.copy(0.3f))
            ) {
                Row(
                    modifier = Modifier.padding(Spacing.Medium),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = Color(0xFF8D4E2C),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(Spacing.Small))
                    Text(
                        text = "VER DISPONIBILIDAD",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF8D4E2C),
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}