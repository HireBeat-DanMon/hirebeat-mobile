package com.hirebeat.com.app.danmon.feature.review.presentation.screens

import com.hirebeat.com.app.danmon.feature.review.domain.entities.Review

data class ReviewUiState(
    val reviews: List<Review> = emptyList(),
    val isLoading: Boolean = false,
    val isSubmitting: Boolean = false,
    val error: String? = null,
    val showAddModal: Boolean = false,
    val hasAlreadyReviewed: Boolean = false,
    val averageRating: Float = 0f,

    val ratingInput: Int = 0,
    val commentInput: String = ""
)