package com.hirebeat.com.app.danmon.feature.profile.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hirebeat.com.app.danmon.core.data.SessionManager
import com.hirebeat.com.app.danmon.core.hardware.domain.*
import com.hirebeat.com.app.danmon.core.permission.domain.PermissionChecker
import com.hirebeat.com.app.danmon.feature.profile.data.datasource.remote.model.*
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.*
import com.hirebeat.com.app.danmon.feature.profile.domain.usecases.*
import com.hirebeat.com.app.danmon.feature.profile.presentation.screens.ProfileUiState
import android.location.Geocoder
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val getMyProfileUseCase: GetMyProfileUseCase,
    private val getCatalogsUseCase: GetCatalogsUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val uploadProfileImageUseCase: UploadProfileImageUseCase,
    private val sessionManager: SessionManager,
    private val vibrateManager: VibrateManager,
    private val flashManager: FlashManager,
    private val locationRepository: LocationRepository,
    private val permissionChecker: PermissionChecker,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        checkRoleAndLoad()
        loadCatalogs()
    }

    private fun checkRoleAndLoad() {
        viewModelScope.launch {
            val role = sessionManager.userRoleName.firstOrNull()
            if (role == "MUSICIAN") {
                loadMyProfile()
            } else {
                _uiState.update { it.copy(isRecruiter = true) }
            }
        }
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

    private fun updateFieldsWithProfile(profile: UserProfile) {
        _uiState.update {
            it.copy(
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
            )
        }
    }

    fun saveProfile() {
        val state = _uiState.value
        if (state.isRecruiter) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            try {
                val request = ProfileSetupRequestDto(
                    city = state.city,
                    experience = state.experience.toIntOrNull() ?: 0,
                    descripcion = state.description,
                    genres = state.selectedGenres,
                    instruments = state.selectedInstruments.map {
                        InstrumentSelectionRequestDto(it.instrument.id, SkillLevel.fromString(it.level).value, it.isPrincipal)
                    },
                    links = listOf(
                        PlataformasProfileRequestDto("Instagram", 1, state.instagramUser),
                        PlataformasProfileRequestDto("Telefono", 2, state.phone),
                        PlataformasProfileRequestDto("Whazap", 3, state.whatsapp)
                    )
                )
                val updated = updateProfileUseCase.execute(request)
                vibrateManager.run()
                updateFieldsWithProfile(updated)
                _uiState.update { it.copy(isSaving = false, isSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isSaving = false, error = e.localizedMessage) }
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

    fun onImageSelected(fileBytes: ByteArray, fileName: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            try {
                val url = uploadProfileImageUseCase.execute(fileBytes, fileName)
                _uiState.update { it.copy(userProfile = it.userProfile?.copy(photoUrl = url), isSaving = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isSaving = false, error = e.localizedMessage) }
            }
        }
    }

    fun useCurrentLocation() {
        if (!permissionChecker.hasLocationPermission()) return
        viewModelScope.launch {
            val loc = locationRepository.getLocationFlow(5000).firstOrNull()
            loc?.let {
                val geocoder = Geocoder(context, Locale.getDefault())
                val address = geocoder.getFromLocation(it.latitude, it.longitude, 1)?.firstOrNull()
                onCityChange(address?.locality ?: "${it.latitude}, ${it.longitude}")
                vibrateManager.run()
            }
        }
    }

    fun loadCatalogs() {
        viewModelScope.launch {
            val (inst, gen) = getCatalogsUseCase.execute()
            _uiState.update { it.copy(availableInstruments = inst, availableGenres = gen) }
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
            s.copy(selectedInstruments = s.selectedInstruments + ProfileInstrument(item, "BASICO", s.selectedInstruments.isEmpty()), showInstrumentModal = false)
        }
    }
    fun updateInstrumentLevel(id: Int, level: Int) {
        val name = SkillLevel.fromInt(level).displayName
        _uiState.update { s -> s.copy(selectedInstruments = s.selectedInstruments.map { if (it.instrument.id == id) it.copy(level = name) else it }) }
    }
    fun togglePrincipalInstrument(id: Int) {
        _uiState.update { s -> s.copy(selectedInstruments = s.selectedInstruments.map { it.copy(isPrincipal = it.instrument.id == id) }) }
    }
    fun removeInstrument(id: Int) {
        _uiState.update { s -> s.copy(selectedInstruments = s.selectedInstruments.filter { it.instrument.id != id }) }
    }
}