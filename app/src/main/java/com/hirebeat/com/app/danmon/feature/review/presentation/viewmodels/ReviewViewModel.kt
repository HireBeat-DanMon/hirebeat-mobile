package com.hirebeat.com.app.danmon.feature.review.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val createReviewUseCase: CreateReviewUseCase
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

                val average = if (reviews.isNotEmpty()) {
                    reviews.map { it.rating }.average().toFloat()
                } else 0f

                _uiState.update { it.copy(
                    reviews = reviews,
                    averageRating = average,
                    isLoading = false
                ) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.localizedMessage) }
            }
        }
    }

    fun submitReview() {
        val rating = uiState.value.ratingInput
        val comment = uiState.value.commentInput

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true) }
            try {
                val request = CreateReviewRequestDto(currentProfileId, rating, comment)
                createReviewUseCase.execute(request)
                _uiState.update { it.copy(isSubmitting = false, showAddModal = false, ratingInput = 0, commentInput = "") }
                loadReviews(currentProfileId)
            } catch (e: Exception) {
                _uiState.update { it.copy(isSubmitting = false, error = e.localizedMessage) }
            }
        }
    }

    fun onRatingChange(newRating: Int) {
        _uiState.update { it.copy(ratingInput = newRating) }
    }

    fun onCommentChange(newComment: String) {
        _uiState.update { it.copy(commentInput = newComment) }
    }

    fun toggleModal(show: Boolean) = _uiState.update { it.copy(showAddModal = show) }
}