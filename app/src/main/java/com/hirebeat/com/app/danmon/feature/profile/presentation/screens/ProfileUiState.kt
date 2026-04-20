package com.hirebeat.com.app.danmon.feature.profile.presentation.screens

import com.hirebeat.com.app.danmon.feature.profile.domain.entities.CatalogItem
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.ProfileInstrument
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.UserProfile

data class ProfileUiState(
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,

    val userProfile: UserProfile? = null,
    val isRecruiter: Boolean = false,

    val city: String = "",
    val phone: String = "",
    val whatsapp: String = "",
    val description: String = "",
    val selectedInstruments: List<ProfileInstrument> = emptyList(),
    val selectedGenres: List<Int> = emptyList(),
    val instagramUser: String = "",

    val showInstrumentModal: Boolean = false,
    val instrumentQuery: String = "",

    val availableInstruments: List<CatalogItem> = emptyList(),
    val availableGenres: List<CatalogItem> = emptyList()
)