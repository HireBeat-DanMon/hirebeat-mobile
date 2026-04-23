package com.hirebeat.com.app.danmon.feature.review.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hirebeat.com.app.danmon.core.data.SessionManager
import com.hirebeat.com.app.danmon.feature.review.data.datasource.remote.model.CreateReviewRequestDto
import com.hirebeat.com.app.danmon.feature.review.domain.usecases.*
import com.hirebeat.com.app.danmon.feature.review.presentation.screens.ReviewUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val getProfileReviewsUseCase: GetProfileReviewsUseCase,
    private val createReviewUseCase: CreateReviewUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReviewUiState())
    val uiState = _uiState.asStateFlow()

    private var currentProfileId: String = ""

    fun loadReviews(profileId: String) {
        currentProfileId = profileId
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val reviews = getProfileReviewsUseCase.execute(profileId)
                _uiState.update { it.copy(reviews = reviews, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.localizedMessage) }
            }
        }
    }

    fun submitReview(rating: Int, comment: String) {
        if (rating == 0 || comment.isBlank()) {
            _uiState.update { it.copy(error = "Calificación y comentario son obligatorios") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true, error = null) }
            try {
                val request = CreateReviewRequestDto(currentProfileId, rating, comment)

                createReviewUseCase.execute(request)

                _uiState.update {
                    it.copy(
                        isSubmitting = false,
                        showAddModal = false,
                        hasAlreadyReviewed = true
                    )
                }
                loadReviews(currentProfileId)
            } catch (e: Exception) {
                _uiState.update { it.copy(isSubmitting = false, error = e.localizedMessage) }
            }
        }
    }

    fun toggleModal(show: Boolean) = _uiState.update { it.copy(showAddModal = show) }
}
