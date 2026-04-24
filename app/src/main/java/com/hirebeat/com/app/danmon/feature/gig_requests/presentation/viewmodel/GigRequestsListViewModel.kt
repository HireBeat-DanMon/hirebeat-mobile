package com.hirebeat.com.app.danmon.feature.gig_requests.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hirebeat.com.app.danmon.feature.gig_requests.domain.usecases.GetMusicianRequestsUseCase
import com.hirebeat.com.app.danmon.feature.gig_requests.domain.usecases.GetRecruiterRequestsUseCase
import com.hirebeat.com.app.danmon.feature.gig_requests.domain.usecases.UpdateGigRequestStatusUseCase
import com.hirebeat.com.app.danmon.feature.gig_requests.presentation.screens.GigRequestsUiState // Importamos el estado de la capa de UI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GigRequestsListViewModel @Inject constructor(
    private val getMusicianRequestsUseCase: GetMusicianRequestsUseCase,
    private val getRecruiterRequestsUseCase: GetRecruiterRequestsUseCase,
    private val updateGigRequestStatusUseCase: UpdateGigRequestStatusUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(GigRequestsUiState())
    val uiState: StateFlow<GigRequestsUiState> = _uiState.asStateFlow()

    init {
        fetchRequests(true)
    }

    fun fetchRequests(isReceived: Boolean) {
        _uiState.update { it.copy(isReceivedTab = isReceived, isLoading = true, error = null) }

        viewModelScope.launch {
            val result = if (isReceived) {
                getMusicianRequestsUseCase()
            } else {
                getRecruiterRequestsUseCase()
            }

            result.fold(
                onSuccess = { list ->
                    _uiState.update { it.copy(isLoading = false, requests = list) }
                },
                onFailure = { err ->
                    _uiState.update { it.copy(isLoading = false, error = err.message) }
                }
            )
        }
    }

    fun updateStatus(requestId: String, newStatus: String) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            updateGigRequestStatusUseCase(requestId, newStatus).fold(
                onSuccess = {
                    fetchRequests(_uiState.value.isReceivedTab)
                },
                onFailure = { err ->
                    _uiState.update { it.copy(isLoading = false, error = err.message) }
                }
            )
        }
    }
}