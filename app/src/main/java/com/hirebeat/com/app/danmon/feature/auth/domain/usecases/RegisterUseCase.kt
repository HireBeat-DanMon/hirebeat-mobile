package com.hirebeat.com.app.danmon.feature.auth.domain.usecases

import com.hirebeat.com.app.danmon.feature.auth.data.datasource.remote.model.RegisterRequestDto
import com.hirebeat.com.app.danmon.feature.auth.domain.entities.AuthToken
import com.hirebeat.com.app.danmon.feature.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend fun execute(request: RegisterRequestDto): AuthToken {
        return repository.register(request)
    }
}