package com.hirebeat.com.app.danmon.feature.review.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hirebeat.com.app.danmon.core.presentation.components.HireBeatTopBar
import com.hirebeat.com.app.danmon.core.theme.Spacing
import com.hirebeat.com.app.danmon.feature.review.presentation.viewmodels.MyReviewsViewModel
import com.hirebeat.com.app.danmon.feature.profile.presentation.components.ReviewCardMock // Puedes renombrarlo a ReviewCard real
import com.hirebeat.com.app.danmon.feature.review.presentation.components.ReviewsSection

@Composable
fun MyReviewsScreen(
    viewModel: MyReviewsViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { HireBeatTopBar(title = "Mis Reseñas", onBackClick = onBack) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
        ) {
            if (state.isLoading && state.reviews.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.reviews.isEmpty()) {
                Text(
                    text = "Aún no tienes reseñas. ¡Sigue tocando!",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(Spacing.Medium)
                ) {
                    item {
                        ReviewsSection(
                            reviews = state.reviews,
                            hasAlreadyReviewed = true,
                            showAddModal = false,
                            isSubmitting = false,
                            onToggleModal = { },
                            onSubmitReview = { _, _ -> }
                        )
                    }
                }
            }

            state.error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.BottomCenter).padding(Spacing.Medium)
                )
            }
        }
    }
}