package com.hirebeat.com.app.danmon.feature.profile.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hirebeat.com.app.danmon.core.presentation.components.HireBeatButton
import com.hirebeat.com.app.danmon.core.theme.*
import com.hirebeat.com.app.danmon.feature.profile.presentation.components.*
import com.hirebeat.com.app.danmon.feature.profile.presentation.viewmodels.ProfileViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MyProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
    onEditProfile: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        return
    }

    val profile = state.userProfile

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(Spacing.Medium),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onBack,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                    )
                ) {
                    Icon(Icons.Default.ArrowBack, "Volver", tint = MaterialTheme.colorScheme.onSurface)
                }
                IconButton(
                    onClick = onLogout,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.9f)
                    )
                ) {
                    Icon(Icons.Default.Logout, "Salir", tint = MaterialTheme.colorScheme.onErrorContainer)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
                AsyncImage(
                    model = profile?.photoUrl,
                    contentDescription = "Foto de perfil",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = android.R.drawable.ic_menu_gallery),
                    error = painterResource(id = android.R.drawable.ic_menu_report_image)
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(0.4f))
                        ))
                )
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-40).dp),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(Spacing.Large)) {

                    Text(
                        text = profile?.fullName ?: "User",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.LocationOn,
                            null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = profile?.city ?: "Tuxtla Gutiérrez",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.History,
                            null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${profile?.experience ?: 0} años de experiencia",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(Spacing.Medium))
                    HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)
                    Spacer(modifier = Modifier.height(Spacing.Medium))

                    SectionTitleView(title = "Mis Instrumentos")

                    if (profile?.instruments.isNullOrEmpty()) {
                        Text(
                            "No hay instrumentos registrados",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(Spacing.Small)
                        )
                    } else {
                        profile?.instruments?.forEach { instrument ->
                            InstrumentItem(
                                profileInstrument = instrument,
                                onLevelChange = {},
                                onTogglePrincipal = {}
                            )
                            Spacer(Modifier.height(Spacing.Small))
                        }
                    }

                    Spacer(modifier = Modifier.height(Spacing.Medium))

                    SectionTitleView(title = "Sobre mí")

                    Text(
                        text = profile?.description ?: "Músico independiente listo para el show...",
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 22.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(Spacing.Medium))

                    SectionTitleView(title = "Géneros")

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        profile?.genres?.forEach { genre ->
                            GenreChip(name = genre.name, isSelected = true, onClick = {})
                        }
                    }

                    Spacer(modifier = Modifier.height(Spacing.Medium))

                    SectionTitleView(title = "Enlaces y Contacto")

                    profile?.links?.forEach { link ->
                        val icon = when(link.name.lowercase()) {
                            "instagram" -> Icons.Default.CameraAlt
                            "telefono", "celular" -> Icons.Default.Phone
                            "whatsapp", "whazap" -> Icons.Default.Chat
                            else -> Icons.Default.Link
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                .padding(Spacing.Small),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                icon,
                                null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = link.ref,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(Spacing.Large))

                    HireBeatButton(
                        text = "Editar mi información",
                        onClick = onEditProfile,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}