package com.hirebeat.com.app.danmon.feature.profile.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hirebeat.com.app.danmon.core.data.SessionManager
import com.hirebeat.com.app.danmon.feature.profile.data.datasource.remote.model.*
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.*
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
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val getAllProfilesUseCases: GetAllProfilesUseCases,
    private val sessionManager: SessionManager
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
                updateFieldsWithProfile(profile)
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.localizedMessage) }
            }
        }
    }

    fun loadProfile(userId: String?) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val profile = if (userId == null || userId == "my_id") {
                    getMyProfileUseCase.execute()
                } else {
                    getAllProfilesUseCases.execute().find { it.id == userId }
                }

                _uiState.update { it.copy(userProfile = profile, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.localizedMessage) }
            }
        }
    }

    private fun updateFieldsWithProfile(profile: UserProfile) {
        _uiState.update { it.copy(
            userProfile = profile,
            isLoading = false,
            description = profile.description,
            city = profile.city,
            experience = profile.experience.toString(),
            selectedInstruments = profile.instruments,
            selectedGenres = profile.genres.map { it.id },
            instagramUser = profile.links.find { it.name.contains("Insta", true) }?.ref ?: "",
            phone = profile.links.find { it.name.contains("Tele", true) }?.ref ?: "",
            whatsapp = profile.links.find { it.name.contains("Wha", true) }?.ref ?: ""
        )}
    }

    fun saveProfile() {
        val state = _uiState.value
        if (state.isRecruiter) {
            _uiState.update { it.copy(error = "Solo músicos pueden editar.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, error = null) }
            try {
                val request = ProfileSetupRequestDto(
                    city = state.city,
                    experience = state.experience.toIntOrNull() ?: 0,
                    descripcion = state.description,
                    genres = state.selectedGenres,
                    instruments = state.selectedInstruments.map {
                        InstrumentSelectionRequestDto(
                            instrumentId = it.instrument.id,
                            level = SkillLevel.fromString(it.level).value,
                            isPrincipal = it.isPrincipal
                        )
                    },
                    links = listOf(
                        PlataformasProfileRequestDto(name = "Instagram", type = 1, ref = state.instagramUser),
                        PlataformasProfileRequestDto(name = "Telefono", type = 2, ref = state.phone),
                        PlataformasProfileRequestDto(name = "Whazap", type = 3, ref = state.whatsapp)
                    )
                )

                val updatedProfile = updateProfileUseCase.execute(request)
                updateFieldsWithProfile(updatedProfile)

                _uiState.update { it.copy(isSaving = false, isSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error: ${e.localizedMessage}", isSaving = false) }
            }
        }
    }

    // Funciones de UI
    fun onExperienceChange(v: String) = _uiState.update { it.copy(experience = v) }
    fun onDescriptionChange(v: String) = _uiState.update { it.copy(description = v) }
    fun onCityChange(v: String) = _uiState.update { it.copy(city = v) }
    fun onPhoneChange(v: String) = _uiState.update { it.copy(phone = v) }
    fun onWhatsAppChange(v: String) = _uiState.update { it.copy(whatsapp = v) }
    fun onInstagramChange(v: String) = _uiState.update { it.copy(instagramUser = v) }

    fun loadCatalogs() {
        viewModelScope.launch {
            try {
                val (inst, gen) = getCatalogsUseCase.execute()
                _uiState.update { it.copy(availableInstruments = inst, availableGenres = gen) }
            } catch (e: Exception) { _uiState.update { it.copy(error = e.message) } }
        }
    }

    fun toggleGenre(id: Int) {
        _uiState.update { s ->
            val list = if (s.selectedGenres.contains(id)) s.selectedGenres - id else s.selectedGenres + id
            s.copy(selectedGenres = list)
        }
    }

    fun onShowModal(s: Boolean) = _uiState.update { it.copy(showInstrumentModal = s) }

    fun addInstrument(item: CatalogItem) {
        _uiState.update { s ->
            if (s.selectedInstruments.any { it.instrument.id == item.id }) return@update s.copy(showInstrumentModal = false)
            val new = ProfileInstrument(item, "BASICO", s.selectedInstruments.isEmpty())
            s.copy(selectedInstruments = s.selectedInstruments + new, showInstrumentModal = false)
        }
    }

    fun updateInstrumentLevel(id: Int, level: Int) {
        val name = SkillLevel.fromInt(level).displayName
        _uiState.update { s ->
            val up = s.selectedInstruments.map { if (it.instrument.id == id) it.copy(level = name) else it }
            s.copy(selectedInstruments = up)
        }
    }

    fun togglePrincipalInstrument(id: Int) {
        _uiState.update { s ->
            val up = s.selectedInstruments.map { it.copy(isPrincipal = it.instrument.id == id) }
            s.copy(selectedInstruments = up)
        }
    }

    fun removeInstrument(id: Int) {
        _uiState.update { s -> s.copy(selectedInstruments = s.selectedInstruments.filter { it.instrument.id != id }) }
    }
}