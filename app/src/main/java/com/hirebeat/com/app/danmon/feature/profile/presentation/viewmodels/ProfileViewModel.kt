package com.hirebeat.com.app.danmon.feature.profile.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hirebeat.com.app.danmon.feature.profile.data.datasource.remote.model.*
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.*
import com.hirebeat.com.app.danmon.feature.profile.domain.usecases.*
import com.hirebeat.com.app.danmon.core.hardware.domain.*
import com.hirebeat.com.app.danmon.core.permission.domain.*
import com.hirebeat.com.app.danmon.feature.profile.presentation.screens.ProfileUiState
import android.location.Geocoder
import java.util.Locale
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getMyProfileUseCase: GetMyProfileUseCase,
    private val getCatalogsUseCase: GetCatalogsUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val getAllProfilesUseCases: GetAllProfilesUseCases,
    private val uploadProfileImageUseCase: UploadProfileImageUseCase,
    private val vibrateManager: VibrateManager,
    private val flashManager: FlashManager,
    private val locationRepository: LocationRepository,
    private val permissionChecker: PermissionChecker,
    @ApplicationContext private val context: Context
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

        val isAnyFieldEmpty = state.city.isBlank() ||
                state.experience.isBlank() ||
                state.phone.isBlank() ||
                state.whatsapp.isBlank() ||
                state.description.isBlank() ||
                state.selectedInstruments.isEmpty() ||
                state.selectedGenres.isEmpty()

        if (isAnyFieldEmpty) {
            _uiState.update { it.copy(error = "Todos los campos son obligatorios") }
            vibrateManager.run()
            return
        }

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
                        PlataformasProfileRequestDto(
                            name = "Instagram",
                            type = 1,
                            ref = state.instagramUser
                        ),
                        PlataformasProfileRequestDto(
                            name = "Telefono",
                            type = 2,
                            ref = state.phone
                        ),
                        PlataformasProfileRequestDto(
                            name = "Whazap",
                            type = 3,
                            ref = state.whatsapp
                        )
                    )
                )

                val updatedProfile = updateProfileUseCase.execute(request)
                vibrateManager.run()
                updateFieldsWithProfile(updatedProfile)

                _uiState.update { it.copy(isSaving = false, isSuccess = true) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = "Error: ${e.localizedMessage}",
                        isSaving = false
                    )
                }
            }
        }
    }

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
                val newImageUrl = uploadProfileImageUseCase.execute(fileBytes, fileName)

                _uiState.update { currentState ->
                    currentState.copy(
                        userProfile = currentState.userProfile?.copy(photoUrl = newImageUrl),
                        isSaving = false
                    )
                }
            } catch (e: Exception) {
                viewModelScope.launch { flashManager.blink(200) }
                _uiState.update { it.copy(
                    error = "Error al subir la imagen: ${e.localizedMessage}",
                    isSaving = false
                ) }
            }
        }
    }

    fun useCurrentLocation() {
        if (!permissionChecker.hasLocationPermission()) {
            _uiState.update { it.copy(error = "Falta permiso de ubicación") }
            return
        }

        viewModelScope.launch {
            try {
                val location = locationRepository.getLocationFlow(5000).firstOrNull()
                location?.let { point ->
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1)

                    if (!addresses.isNullOrEmpty()) {
                        val address = addresses[0]
                        val cityName = address.locality ?: address.subAdminArea ?: address.adminArea ?: "Ciudad desconocida"

                        onCityChange(cityName)
                    } else {
                        onCityChange("${point.latitude}, ${point.longitude}")
                    }

                    vibrateManager.run()
                }
            } catch (e: Exception) {
                flashManager.blink(200)
                _uiState.update { it.copy(error = "Error al traducir ubicación: ${e.message}") }
            }
        }
    }

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