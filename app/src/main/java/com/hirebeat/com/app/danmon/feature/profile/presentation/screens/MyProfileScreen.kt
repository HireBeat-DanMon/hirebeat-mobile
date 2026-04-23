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
                    modifier = Modifier.background(Color.Black.copy(0.3f), CircleShape)
                ) {
                    Icon(Icons.Default.ArrowBack, "Volver", tint = Color.White)
                }
                IconButton(
                    onClick = onLogout,
                    modifier = Modifier.background(MaterialTheme.colorScheme.error.copy(0.8f), CircleShape)
                ) {
                    Icon(Icons.Default.Logout, "Salir", tint = Color.White)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollState)
        ) {
            Box(modifier = Modifier.fillMaxWidth().height(260.dp)) {
                AsyncImage(
                    model = profile?.photoUrl,
                    contentDescription = "Foto de perfil de ${profile?.fullName}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = android.R.drawable.ic_menu_gallery),
                    error = painterResource(id = android.R.drawable.ic_menu_report_image)
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(0.5f))
                        ))
                )
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-30).dp),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(modifier = Modifier.padding(Spacing.Large)) {

                    Text(
                        text = profile?.fullName ?: "Daniel Camacho Morales",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, null, tint = Color(0xFFC97E58), modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = profile?.city ?: "Tuxtla Gutiérrez", style = MaterialTheme.typography.bodyLarge)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.History, null, tint = Color(0xFFC97E58), modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${profile?.experience ?: 0} años de experiencia",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(Spacing.Medium))
                    HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
                    Spacer(modifier = Modifier.height(Spacing.Medium))

                    SectionTitleView(title = "Mis Instrumentos", iconColor = Color(0xFF8D4E2C))
                    profile?.instruments?.forEach { instrument ->
                        InstrumentItem(
                            profileInstrument = instrument,
                            onLevelChange = { },
                            onTogglePrincipal = { }
                        )
                    } ?: Text("No hay instrumentos registrados", style = MaterialTheme.typography.bodySmall)

                    Spacer(modifier = Modifier.height(Spacing.Medium))

                    SectionTitleView(title = "Sobre mí", iconColor = Color(0xFFC97E58))
                    Text(
                        text = profile?.description ?: "Músico independiente que toca marimba...",
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(Spacing.Medium))

                    SectionTitleView(title = "Géneros", iconColor = Color(0xFF8D4E2C))
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

                    SectionTitleView(title = "Enlaces y Contacto", iconColor = Color(0xFF5C4033))
                    profile?.links?.forEach { link ->
                        val icon = when(link.name.lowercase()) {
                            "instagram" -> Icons.Default.CameraAlt
                            "telefono" -> Icons.Default.Phone
                            "whatsapp", "whazap" -> Icons.Default.Chat
                            else -> Icons.Default.Link
                        }
                        Row(
                            modifier = Modifier.padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(text = link.ref, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                        }
                    }

                    HireBeatButton(
                        text = "Editar mi información",
                        onClick = onEditProfile,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Spacing.Medium, vertical = Spacing.Medium)
                    )
                }
            }
        }
    }
}