package com.hirebeat.com.app.danmon.feature.profile.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.hirebeat.com.app.danmon.core.theme.Spacing
import com.hirebeat.com.app.danmon.feature.profile.presentation.components.*
import com.hirebeat.com.app.danmon.feature.profile.presentation.viewmodels.ProfileViewModel
import com.hirebeat.com.app.danmon.feature.gig_requests.presentation.viewmodels.GigRequestViewModel
import com.hirebeat.com.app.danmon.feature.gig_requests.presentation.screens.GigRequestFormScreen
import com.hirebeat.com.app.danmon.feature.gig_requests.presentation.components.GigDatePickerDialog
import com.hirebeat.com.app.danmon.feature.gig_requests.presentation.components.GigTimePickerDialog
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userId: String,
    onBack: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
    gigViewModel: GigRequestViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val gigState by gigViewModel.uiState.collectAsState()

    var showGigForm by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

    LaunchedEffect(userId) {
        viewModel.loadProfile(userId)
    }

    LaunchedEffect(gigState.isSuccess) {
        if (gigState.isSuccess) {
            showGigForm = false
            gigViewModel.resetSuccess()
        }
    }

    Scaffold(
        bottomBar = {
            Surface(
                color = Color.White.copy(alpha = 0.95f),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.padding(Spacing.Medium),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = Spacing.Medium),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ContactIconButton(Icons.Default.ChatBubbleOutline, Color(0xFFE2D1CA)) { }
                        ContactIconButton(Icons.Default.Phone, Color(0xFFFCEAE3)) { }
                        ContactIconButton(Icons.Default.Email, Color(0xFFF0DFD8)) { }
                    }

                    val dateText = gigState.selectedDate?.let {
                        val formatter = DateTimeFormatter.ofPattern("dd MMM", Locale("es", "ES"))
                        " PARA EL ${it.format(formatter).uppercase()}"
                    } ?: ""

                    Button(
                        onClick = { showGigForm = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8D4E2C))
                    ) {
                        Icon(Icons.Default.Schedule, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "ENVIAR SOLICITUD$dateText",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            val profile = uiState.userProfile
            if (profile != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                ) {
                    Box(modifier = Modifier.fillMaxWidth().height(260.dp)) {
                        AsyncImage(
                            model = profile.photoUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
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
                                onClick = { },
                                modifier = Modifier.background(Color.Black.copy(0.3f), CircleShape)
                            ) {
                                Icon(Icons.Default.Share, "Compartir", tint = Color.White)
                            }
                        }
                    }

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = (-24).dp),
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Column(modifier = Modifier.padding(Spacing.Large)) {
                            ProfileHeaderSection(
                                name = profile.fullName,
                                city = profile.city,
                                instruments = profile.instruments
                            )

                            Spacer(modifier = Modifier.height(Spacing.Large))

                            SectionTitleView(title = "Calendario de Disponibilidad", iconColor = Color(0xFF8D4E2C))
                            Text(
                                "Selecciona una fecha disponible para tu evento.\n(ROJO = OCUPADO)",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                modifier = Modifier.padding(bottom = Spacing.Small)
                            )

                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = Color.White,
                                shadowElevation = 2.dp
                            ) {
                                ProfileCalendarMaterial(
                                    onDateSelected = { millis ->
                                        if (millis != null) {
                                            val date = Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDate()
                                            gigViewModel.onDateSelected(date)
                                        }
                                    }
                                )
                            }

                            Spacer(modifier = Modifier.height(Spacing.Large))

                            SectionTitleView(title = "Sobre mí", iconColor = Color(0xFF8D4E2C))
                            Text(
                                text = profile.description.ifEmpty { "Sin descripción" },
                                style = MaterialTheme.typography.bodyMedium,
                                lineHeight = 20.sp,
                                color = Color.DarkGray
                            )

                            Spacer(modifier = Modifier.height(Spacing.Large))

                            SectionTitleView(title = "Especialidad", iconColor = Color(0xFFC97E58))
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                profile.genres.forEach { genre ->
                                    Surface(
                                        color = Color(0xFF3B6B61),
                                        shape = RoundedCornerShape(20.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(Icons.Default.MusicNote, null, tint = Color.White, modifier = Modifier.size(16.dp))
                                            Spacer(Modifier.width(4.dp))
                                            Text(genre.name, color = Color.White, style = MaterialTheme.typography.bodyMedium)
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(Spacing.Large))

                            SectionTitleView(title = "Reseñas y Calificaciones", iconColor = Color(0xFF5C4033))
                            ReviewCardMock()
                            Spacer(modifier = Modifier.height(Spacing.Small))
                            ReviewCardMock()

                            Spacer(modifier = Modifier.height(80.dp))
                        }
                    }
                }
            } else {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Perfil no encontrado")
                }
            }
        }
    }

    if (showGigForm) {
        Dialog(onDismissRequest = { showGigForm = false }) {
            GigRequestFormScreen(
                state = gigState,
                onClose = { showGigForm = false },
                onDateClick = { showDatePicker = true },
                onStartTimeClick = { showStartTimePicker = true },
                onEndTimeClick = { showEndTimePicker = true },
                onLocationChange = gigViewModel::onLocationChange,
                onPaymentChange = gigViewModel::onPaymentChange,
                onDetailsChange = gigViewModel::onDetailsChange,
                onSubmit = {
                    gigViewModel.submitGigRequest(musicianId = userId)
                }
            )
        }
    }

    if (showDatePicker) {
        GigDatePickerDialog(
            onDismiss = { showDatePicker = false },
            onDateSelected = { gigViewModel.onDateSelected(it) }
        )
    }

    if (showStartTimePicker) {
        GigTimePickerDialog(
            title = "Hora de Inicio",
            onDismiss = { showStartTimePicker = false },
            onTimeSelected = { gigViewModel.onStartTimeSelected(it) }
        )
    }

    if (showEndTimePicker) {
        GigTimePickerDialog(
            title = "Hora de Fin",
            onDismiss = { showEndTimePicker = false },
            onTimeSelected = { gigViewModel.onEndTimeSelected(it) }
        )
    }
}