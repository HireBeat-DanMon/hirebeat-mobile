package com.hirebeat.com.app.danmon.feature.profile.domain.usecases

import com.hirebeat.com.app.danmon.feature.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

class UploadProfileImageUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend fun execute(fileBytes: ByteArray, fileName: String): String {
        return repository.uploadImage(fileBytes, fileName)
    }
}