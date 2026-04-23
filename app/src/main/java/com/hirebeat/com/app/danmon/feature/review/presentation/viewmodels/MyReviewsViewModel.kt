package com.hirebeat.com.app.danmon.feature.review.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hirebeat.com.app.danmon.feature.review.domain.usecases.GetMyReviewsUseCase
import com.hirebeat.com.app.danmon.feature.review.presentation.screens.MyReviewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyReviewsViewModel @Inject constructor(
    private val getMyReviewsUseCase: GetMyReviewsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyReviewsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadMyReviews()
    }

    private fun loadMyReviews() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val reviews = getMyReviewsUseCase.execute()
                _uiState.update { it.copy(reviews = reviews, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.localizedMessage) }
            }
        }
    }
}