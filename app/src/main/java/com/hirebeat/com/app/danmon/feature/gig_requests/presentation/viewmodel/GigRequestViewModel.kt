package com.hirebeat.com.app.danmon.feature.gig_requests.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hirebeat.com.app.danmon.feature.gig_requests.domain.usecases.CreateGigRequestUseCase
import com.hirebeat.com.app.danmon.feature.gig_requests.presentation.screens.GigRequestFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class GigRequestViewModel @Inject constructor(
    private val createGigRequestUseCase: CreateGigRequestUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(GigRequestFormState())
    val uiState: StateFlow<GigRequestFormState> = _uiState.asStateFlow()

    fun onLocationChange(location: String) { _uiState.update { it.copy(location = location) } }
    fun onPaymentChange(payment: String) { _uiState.update { it.copy(paymentOffered = payment) } }
    fun onDetailsChange(details: String) { _uiState.update { it.copy(additionalDetails = details) } }

    fun onDateSelected(date: LocalDate) {
        _uiState.update { it.copy(selectedDate = date) }
    }

    fun onStartTimeSelected(time: LocalTime) {
        _uiState.update { it.copy(startTime = time) }
    }

    fun onEndTimeSelected(time: LocalTime) {
        _uiState.update { it.copy(endTime = time) }
    }

    fun resetSuccess() {
        _uiState.update { it.copy(isSuccess = false) }
    }

    fun submitGigRequest(musicianId: String) {
        val state = _uiState.value

        if (state.selectedDate == null || state.startTime == null || state.endTime == null) {
            _uiState.update { it.copy(error = "Por favor selecciona fecha y horas") }
            return
        }

        val startTimeCompleto = LocalDateTime.of(state.selectedDate, state.startTime)
        var endTimeCompleto = LocalDateTime.of(state.selectedDate, state.endTime)

        if (state.endTime.isBefore(state.startTime)) {
            endTimeCompleto = endTimeCompleto.plusDays(1)
        }

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            val payment = state.paymentOffered.toDoubleOrNull() ?: 0.0

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            val startStr = startTimeCompleto.format(formatter)
            val endStr = endTimeCompleto.format(formatter)

            val result = createGigRequestUseCase(
                musicianProfileId = musicianId,
                startTime = startStr,
                endTime = endStr,
                location = state.location,
                paymentOffered = payment,
                messageDetails = state.additionalDetails
            )

            result.fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false, error = null, isSuccess = true) }
                },
                onFailure = { error ->
                    _uiState.update { it.copy(isLoading = false, error = error.message) }
                }
            )
        }
    }
}