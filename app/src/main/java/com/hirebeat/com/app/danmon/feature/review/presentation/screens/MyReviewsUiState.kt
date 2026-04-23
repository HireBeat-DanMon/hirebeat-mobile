package com.hirebeat.com.app.danmon.feature.review.presentation.screens

import com.hirebeat.com.app.danmon.feature.review.domain.entities.Review

data class MyReviewsUiState(
    val reviews: List<Review> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)