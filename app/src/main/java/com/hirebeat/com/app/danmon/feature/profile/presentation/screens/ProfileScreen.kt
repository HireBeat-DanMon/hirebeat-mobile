package com.hirebeat.com.app.danmon.feature.profile.presentation.screens

import com.hirebeat.com.app.danmon.feature.review.presentation.screens.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.hirebeat.com.app.danmon.feature.review.presentation.components.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.hirebeat.com.app.danmon.core.theme.*
import com.hirebeat.com.app.danmon.feature.profile.presentation.components.*
import com.hirebeat.com.app.danmon.feature.profile.presentation.viewmodels.ProfileDetailViewModel
import com.hirebeat.com.app.danmon.feature.review.presentation.viewmodels.ReviewViewModel

@Composable
fun ProfileScreen(
    userId: String,
    viewModel: ProfileDetailViewModel = hiltViewModel(),
    reviewViewModel: ReviewViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val reviewState by reviewViewModel.uiState.collectAsStateWithLifecycle()
    val profile = state.userProfile

    LaunchedEffect(userId) {
        viewModel.loadProfile(userId)
        reviewViewModel.loadReviews(userId)
    }

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (profile == null && state.error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = state.error ?: "Error al cargar el perfil", color = Color.Red)
        }
        return
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(Spacing.Medium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.background(Color.Black.copy(alpha = 0.3f), CircleShape)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                }
            }
        },
        bottomBar = {
            ProfileContactBar(profile)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(scrollState)
            ) {

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

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-Spacing.Large)),
                    shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.padding(Spacing.Large),
                        verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
                    ) {
                        ProfileHeaderSection(
                            name = profile?.fullName ?: "Usuario",
                            city = profile?.city ?: "Ubicación no disponible",
                            instruments = profile?.instruments ?: emptyList()
                        )

                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outlineVariant.copy(
                                alpha = 0.5f
                            )
                        )

                        SectionTitleView(title = "Sobre mí", iconColor = Color(0xFF8D4E2C))
                        Text(
                            text = profile?.description ?: "Sin descripción disponible.",
                            style = MaterialTheme.typography.bodyMedium,
                            lineHeight = 22.sp,
                            color = Color.Black.copy(alpha = 0.8f)
                        )

                        if (profile?.instruments?.isNotEmpty() == true) {
                            SectionTitleView(title = "Instrumentos", iconColor = Color(0xFF4A6572)) // Puedes cambiar el color
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
                                verticalArrangement = Arrangement.spacedBy(Spacing.Small),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                profile.instruments.forEach { instrument ->
                                    GenreChip(
                                        name = instrument.instrument.name,
                                        isSelected = true,
                                        onClick = {}
                                    )
                                }
                            }
                        }

                        if (profile?.genres?.isNotEmpty() == true) {
                            SectionTitleView(title = "Especialidad", iconColor = Color(0xFFC97E58))
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
                                verticalArrangement = Arrangement.spacedBy(Spacing.Small),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                profile.genres.forEach { genre ->
                                    GenreChip(name = genre.name, isSelected = true, onClick = {})
                                }
                            }
                        }

                        SectionTitleView(
                            title = "Reseñas y Calificaciones",
                            iconColor = Color(0xFF5C4033)
                        )

                        ReviewsSection(
                            reviews = reviewState.reviews,
                            hasAlreadyReviewed = reviewState.hasAlreadyReviewed,
                            showAddModal = reviewState.showAddModal,
                            isSubmitting = reviewState.isSubmitting,
                            onToggleModal = { reviewViewModel.toggleModal(it) },
                            onSubmitReview = { rating, comment ->
                                reviewViewModel.submitReview(rating, comment)
                            }
                        )

                        if (reviewState.showAddModal) {
                            AddReviewModal(
                                onDismiss = { reviewViewModel.toggleModal(false) },
                                onSubmit = { rating, comment ->
                                    reviewViewModel.submitReview(rating, comment)
                                },
                                isSubmitting = reviewState.isSubmitting
                            )
                        }

                        Spacer(modifier = Modifier.height(innerPadding.calculateBottomPadding() + Spacing.Small))
                    }
                }
            }
        }
    }
}
