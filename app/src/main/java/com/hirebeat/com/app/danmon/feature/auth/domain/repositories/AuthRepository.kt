package com.hirebeat.com.app.danmon.feature.auth.domain.repositories

import com.hirebeat.com.app.danmon.feature.auth.data.datasource.remote.model.LoginRequestDto
import com.hirebeat.com.app.danmon.feature.auth.data.datasource.remote.model.RegisterRequestDto
import com.hirebeat.com.app.danmon.feature.auth.domain.entities.AuthToken

interface AuthRepository {
    suspend fun login(request: LoginRequestDto): AuthToken
    suspend fun register(request: RegisterRequestDto): AuthToken
}
