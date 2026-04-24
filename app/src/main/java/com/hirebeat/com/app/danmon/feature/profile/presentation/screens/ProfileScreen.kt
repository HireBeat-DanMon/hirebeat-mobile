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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.hirebeat.com.app.danmon.core.theme.Spacing
import com.hirebeat.com.app.danmon.feature.gig_requests.presentation.components.*
import com.hirebeat.com.app.danmon.feature.gig_requests.presentation.screens.*
import com.hirebeat.com.app.danmon.feature.gig_requests.presentation.viewmodel.GigRequestViewModel
import com.hirebeat.com.app.danmon.feature.profile.presentation.components.*
import com.hirebeat.com.app.danmon.feature.profile.presentation.viewmodels.ProfileDetailViewModel
import com.hirebeat.com.app.danmon.feature.review.presentation.components.*
import com.hirebeat.com.app.danmon.feature.review.presentation.screens.AddReviewModal
import com.hirebeat.com.app.danmon.feature.review.presentation.viewmodels.ReviewViewModel
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userId: String,
    onBack: () -> Unit,
    viewModel: ProfileDetailViewModel = hiltViewModel(),
    reviewViewModel: ReviewViewModel = hiltViewModel(),
    gigViewModel: GigRequestViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val reviewState by reviewViewModel.uiState.collectAsStateWithLifecycle()
    val gigState by gigViewModel.uiState.collectAsStateWithLifecycle()

    val profile = state.userProfile

    var showGigForm by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

    LaunchedEffect(userId) {
        viewModel.loadProfile(userId)
        reviewViewModel.loadReviews(userId)
    }

    LaunchedEffect(gigState.isSuccess) {
        if (gigState.isSuccess) {
            showGigForm = false
            gigViewModel.resetSuccess()
        }
    }

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        return
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 12.dp
            ) {
                Column(
                    modifier = Modifier.padding(Spacing.Medium),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = Spacing.Small),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ProfileContactBar(profile)
                    }

                    val dateText = gigState.selectedDate?.let {
                        val formatter = DateTimeFormatter.ofPattern("dd MMM", Locale("es", "ES"))
                        " PARA EL ${it.format(formatter).uppercase()}"
                    } ?: ""

                    Button(
                        onClick = { showGigForm = true },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8D4E2C))
                    ) {
                        Icon(Icons.Default.Schedule, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("ENVIAR SOLICITUD$dateText", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { innerPadding ->
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
                    placeholder = painterResource(id = android.R.drawable.ic_menu_gallery)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(Spacing.Medium),
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
                modifier = Modifier.fillMaxWidth().offset(y = (-24).dp),
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp
            ) {
                Column(
                    modifier = Modifier.padding(Spacing.Large),
                    verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
                ) {
                    ProfileHeaderSection(
                        name = profile?.fullName ?: "Músico HireBeat",
                        city = profile?.city ?: "Chiapas, México",
                        instruments = profile?.instruments ?: emptyList(),
                        averageRating = reviewState.averageRating
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                    SectionTitleView(title = "Disponibilidad", indicatorColor = Color(0xFF8D4E2C))
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        modifier = Modifier.padding(vertical = 8.dp)
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

                    SectionTitleView(title = "Sobre mí")
                    Text(
                        text = profile?.description?.ifEmpty { "Este músico aún no ha agregado una descripción." } ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    if (profile?.instruments?.isNotEmpty() == true) {
                        SectionTitleView(title = "Instrumentos", indicatorColor = MaterialTheme.colorScheme.secondary)
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            profile.instruments.forEach { inst ->
                                GenreChip(name = inst.instrument.name, isSelected = true, onClick = {})
                            }
                        }
                    }

                    if (profile?.genres?.isNotEmpty() == true) {
                        SectionTitleView(title = "Generos", indicatorColor = MaterialTheme.colorScheme.secondary)
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            profile.genres.forEach { g ->
                                GenreChip(name = g.name, isSelected = true, onClick = {})
                            }
                        }
                    }

                    SectionTitleView(title = "Reseñas y Calificaciones", indicatorColor = MaterialTheme.colorScheme.outline)
                    ReviewsSection(
                        reviews = reviewState.reviews,
                        ratingInput = reviewState.ratingInput,
                        commentInput = reviewState.commentInput,
                        onRatingChange = reviewViewModel::onRatingChange,
                        onCommentChange = reviewViewModel::onCommentChange,
                        hasAlreadyReviewed = reviewState.hasAlreadyReviewed,
                        showAddModal = reviewState.showAddModal,
                        isSubmitting = reviewState.isSubmitting,
                        onToggleModal = reviewViewModel::toggleModal,
                        onSubmitReview = reviewViewModel::submitReview
                    )

                    Spacer(modifier = Modifier.height(innerPadding.calculateBottomPadding() + 40.dp))
                }
            }
        }
    }

    if (reviewState.showAddModal) {
        AddReviewModal(
            rating = reviewState.ratingInput,
            comment = reviewState.commentInput,
            onRatingChange = reviewViewModel::onRatingChange,
            onCommentChange = reviewViewModel::onCommentChange,
            onDismiss = { reviewViewModel.toggleModal(false) },
            onSubmit = reviewViewModel::submitReview,
            isSubmitting = reviewState.isSubmitting
        )
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
                onSubmit = { gigViewModel.submitGigRequest(musicianId = userId) }
            )
        }
    }

    if (showDatePicker) {
        GigDatePickerDialog(onDismiss = { showDatePicker = false }, onDateSelected = gigViewModel::onDateSelected)
    }
    if (showStartTimePicker) {
        GigTimePickerDialog(title = "Inicio", onDismiss = { showStartTimePicker = false }, onTimeSelected = gigViewModel::onStartTimeSelected)
    }
    if (showEndTimePicker) {
        GigTimePickerDialog(title = "Fin", onDismiss = { showEndTimePicker = false }, onTimeSelected = gigViewModel::onEndTimeSelected)
    }
}