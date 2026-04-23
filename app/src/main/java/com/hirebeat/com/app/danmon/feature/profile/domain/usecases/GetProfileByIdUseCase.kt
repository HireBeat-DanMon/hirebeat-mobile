package com.hirebeat.com.app.danmon.feature.profile.domain.usecases

import com.hirebeat.com.app.danmon.feature.profile.domain.entities.UserProfile
import com.hirebeat.com.app.danmon.feature.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

class GetProfileByIdUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend fun execute(userId: String): UserProfile {
        return repository.getProfileById(userId)
    }
}