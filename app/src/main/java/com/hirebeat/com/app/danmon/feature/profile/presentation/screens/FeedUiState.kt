package com.hirebeat.com.app.danmon.feature.profile.presentation.screens

import com.hirebeat.com.app.danmon.feature.profile.domain.entities.UserProfile

data class FeedUiState(
    val profiles: List<UserProfile> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)