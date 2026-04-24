package com.hirebeat.com.app.danmon.feature.gig_requests.presentation.screens

import com.hirebeat.com.app.danmon.feature.gig_requests.domain.entities.GigRequestItem

data class GigRequestsUiState(
    val requests: List<GigRequestItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isReceivedTab: Boolean = true
)