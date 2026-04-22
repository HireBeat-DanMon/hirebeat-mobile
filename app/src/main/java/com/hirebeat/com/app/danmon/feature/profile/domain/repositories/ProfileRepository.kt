package com.hirebeat.com.app.danmon.feature.profile.domain.repositories

import com.hirebeat.com.app.danmon.feature.profile.data.datasource.remote.model.ProfileSetupRequestDto
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.*

interface ProfileRepository {
    suspend fun getMyProfile(): UserProfile
    suspend fun updateProfile(request: ProfileSetupRequestDto): UserProfile

    suspend fun uploadImage(fileBytes: ByteArray, fileName: String): String
    suspend fun getAllProfiles(): List<UserProfile>
    suspend fun getInstruments(): List<CatalogItem>
    suspend fun getGenres(): List<CatalogItem>
}
