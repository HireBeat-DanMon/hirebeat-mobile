package com.hirebeat.com.app.danmon.feature.profile.domain.usecases

import com.hirebeat.com.app.danmon.feature.profile.data.datasource.remote.model.ProfileSetupRequestDto
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.UserProfile
import com.hirebeat.com.app.danmon.feature.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend fun execute(request: ProfileSetupRequestDto): UserProfile {
        return repository.updateProfile(request)
    }
}