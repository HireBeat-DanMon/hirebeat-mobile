package com.hirebeat.com.app.danmon.feature.profile.domain.usecases

import com.hirebeat.com.app.danmon.feature.profile.domain.entities.UserProfile
import com.hirebeat.com.app.danmon.feature.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

class GetAllProfilesUseCases @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend fun execute(): List<UserProfile> = repository.getAllProfiles()
}