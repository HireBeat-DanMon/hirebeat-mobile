package com.hirebeat.com.app.danmon.feature.profile.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.UserProfile
import com.hirebeat.com.app.danmon.feature.profile.domain.usecases.GetAllProfilesUseCases
import com.hirebeat.com.app.danmon.feature.profile.presentation.screens.FeedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getAllProfilesUseCases: GetAllProfilesUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(FeedUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadProfiles()
    }

    fun loadProfiles() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val profiles = getAllProfilesUseCases.execute()
                _uiState.update { it.copy(profiles = profiles, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.localizedMessage) }
            }
        }
    }
}