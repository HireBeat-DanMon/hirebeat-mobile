package com.hirebeat.com.app.danmon.feature.profile.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hirebeat.com.app.danmon.core.data.SessionManager
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.*
import com.hirebeat.com.app.danmon.feature.profile.domain.usecases.*
import com.hirebeat.com.app.danmon.feature.profile.presentation.screens.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val getMyProfileUseCase: GetMyProfileUseCase,
    private val getCatalogsUseCase: GetCatalogsUseCase,
    private val sessionManager: SessionManager,
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

    fun loadCatalogs() {
        viewModelScope.launch {
            val (inst, gen) = getCatalogsUseCase.execute()
            _uiState.update { it.copy(availableInstruments = inst, availableGenres = gen) }
        }
    }

}