package com.hirebeat.com.app.danmon.feature.profile.presentation.screens

import com.hirebeat.com.app.danmon.feature.review.presentation.screens.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@OptIn(ExperimentalLayoutApi::class)
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
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        return
    }

    if (profile == null && state.error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = state.error ?: "Error al cargar el perfil",
                color = MaterialTheme.colorScheme.error
            )
        }
        return
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(Spacing.Medium),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = onBack,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Atrás",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        bottomBar = {
            ProfileContactBar(profile)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            AsyncImage(
                model = profile?.photoUrl,
                contentDescription = "Foto de perfil",
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

                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outlineVariant
                    )

                    SectionTitleView(title = "Sobre mí")
                    Text(
                        text = profile?.description ?: "Este músico aún no ha agregado una descripción.",
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 22.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    if (profile?.instruments?.isNotEmpty() == true) {
                        SectionTitleView(
                            title = "Instrumentos",
                            indicatorColor = MaterialTheme.colorScheme.secondary
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
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
                        SectionTitleView(
                            title = "Especialidad",
                            indicatorColor = MaterialTheme.colorScheme.tertiary
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            profile.genres.forEach { genre ->
                                GenreChip(name = genre.name, isSelected = true, onClick = {})
                            }
                        }
                    }

                    SectionTitleView(
                        title = "Reseñas y Calificaciones",
                        indicatorColor = MaterialTheme.colorScheme.outline
                    )

                    ReviewsSection(
                        reviews = reviewState.reviews,
                        ratingInput = reviewState.ratingInput,
                        commentInput = reviewState.commentInput,
                        onRatingChange = { reviewViewModel.onRatingChange(it) },
                        onCommentChange = { reviewViewModel.onCommentChange(it) },
                        hasAlreadyReviewed = reviewState.hasAlreadyReviewed,
                        showAddModal = reviewState.showAddModal,
                        isSubmitting = reviewState.isSubmitting,
                        onToggleModal = { reviewViewModel.toggleModal(it) },
                        onSubmitReview = { reviewViewModel.submitReview() }
                    )

                    Spacer(modifier = Modifier.height(innerPadding.calculateBottomPadding() + 24.dp))
                }
            }
        }
    }

    if (reviewState.showAddModal) {
        AddReviewModal(
            rating = reviewState.ratingInput,
            comment = reviewState.commentInput,
            onRatingChange = { reviewViewModel.onRatingChange(it) },
            onCommentChange = { reviewViewModel.onCommentChange(it) },
            onDismiss = { reviewViewModel.toggleModal(false) },
            onSubmit = {
                reviewViewModel.submitReview()
            },
            isSubmitting = reviewState.isSubmitting
        )
    }
}
