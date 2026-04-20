package com.hirebeat.com.app.danmon.feature.profile.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hirebeat.com.app.danmon.feature.profile.data.datasource.remote.model.*
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.CatalogItem
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.ProfileInstrument
import com.hirebeat.com.app.danmon.feature.profile.domain.usecases.*
import com.hirebeat.com.app.danmon.feature.profile.presentation.screens.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getMyProfileUseCase: GetMyProfileUseCase,
    private val getCatalogsUseCase: GetCatalogsUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadMyProfile()
        loadCatalogs()
    }

    private fun loadMyProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val profile = getMyProfileUseCase.execute()

                // Actualizamos el estado con los datos del perfil para que aparezcan en los campos al editar
                _uiState.update { it.copy(
                    userProfile = profile,
                    isLoading = false,
                    isRecruiter = profile.role == "Recruiter",
                    description = profile.description,
                    city = profile.city,
                    selectedInstruments = profile.instruments,
                    selectedGenres = profile.genres.map { genre -> genre.id },
                    // Buscamos links específicos si existen
                    phone = profile.links.find { l -> l.name.lowercase() == "telefono" }?.ref ?: "",
                    whatsapp = profile.links.find { l -> l.name.lowercase() == "whatsapp" }?.ref ?: "",
                    instagramUser = profile.links.find { l -> l.name.lowercase() == "instagram" }?.ref ?: ""
                )}
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    private fun loadCatalogs() {
        viewModelScope.launch {
            try {
                val (instruments, genres) = getCatalogsUseCase.execute()
                _uiState.update { it.copy(
                    availableInstruments = instruments,
                    availableGenres = genres
                )}
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun onDescriptionChange(desc: String) = _uiState.update { it.copy(description = desc) }
    fun onCityChange(city: String) = _uiState.update { it.copy(city = city) }
    fun onPhoneChange(value: String) = _uiState.update { it.copy(phone = value) }
    fun onWhatsAppChange(value: String) = _uiState.update { it.copy(whatsapp = value) }
    fun onInstagramChange(value: String) = _uiState.update { it.copy(instagramUser = value) }

    fun updateInstrumentLevel(instrumentId: Int, newLevel: Int) {
        val levelString = when(newLevel) {
            1 -> "BASICO"
            2 -> "PRINCIPIANTE"
            3 -> "INTERMEDIO"
            4 -> "AVANZADO"
            5 -> "PROFESIONAL"
            else -> "BASICO"
        }
        _uiState.update { state ->
            val updated = state.selectedInstruments.map {
                if (it.instrument.id == instrumentId) it.copy(level = levelString) else it
            }
            state.copy(selectedInstruments = updated)
        }
    }

    fun togglePrincipalInstrument(instrumentId: Int) {
        _uiState.update { state ->
            val updated = state.selectedInstruments.map {
                it.copy(isPrincipal = it.instrument.id == instrumentId)
            }
            state.copy(selectedInstruments = updated)
        }
    }

    fun removeInstrument(instrumentId: Int) {
        _uiState.update { state ->
            state.copy(selectedInstruments = state.selectedInstruments.filter { it.instrument.id != instrumentId })
        }
    }

    fun saveProfile() {
        val state = _uiState.value

        // SEGURIDAD: Si es reclutador, no permitimos guardar (el flujo de músico es distinto)
        if (state.isRecruiter) {
            _uiState.update { it.copy(error = "Los reclutadores no pueden configurar perfil de músico") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            try {
                val request = ProfileSetupRequestDto(
                    city = state.city,
                    experience = 3, // Valor por defecto o podrías añadir un campo
                    descripcion = state.description,
                    genres = state.selectedGenres,
                    instruments = state.selectedInstruments.map {
                        InstrumentSelectionRequestDto(
                            instrumentId = it.instrument.id,
                            level = it.level,
                            isPrincipal = it.isPrincipal
                        )
                    },
                    links = listOf(
                        PlataformasProfileRequestDto("Instagram", 1, state.instagramUser),
                        PlataformasProfileRequestDto("Telefono", 2, state.phone),
                        PlataformasProfileRequestDto("WhatsApp", 3, state.whatsapp)
                    )
                )
                updateProfileUseCase.execute(request)
                _uiState.update { it.copy(isSaving = false, isSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isSaving = false) }
            }
        }
    }

    fun toggleGenre(genreId: Int) {
        _uiState.update { state ->
            val current = state.selectedGenres
            val next = if (current.contains(genreId)) {
                current.filter { it != genreId }
            } else {
                current + genreId
            }
            state.copy(selectedGenres = next)
        }
    }

    fun onShowModal(show: Boolean) = _uiState.update { it.copy(showInstrumentModal = show) }

    fun addInstrument(catalogItem: CatalogItem) {
        _uiState.update { state ->
            if (state.selectedInstruments.any { it.instrument.id == catalogItem.id }) {
                return@update state.copy(showInstrumentModal = false)
            }
            val newInstrument = ProfileInstrument(
                instrument = catalogItem,
                level = "BASICO",
                isPrincipal = state.selectedInstruments.isEmpty()
            )
            state.copy(
                selectedInstruments = state.selectedInstruments + newInstrument,
                showInstrumentModal = false
            )
        }
    }
}