package com.hirebeat.com.app.danmon.feature.auth.data.repositories

import com.hirebeat.com.app.danmon.core.data.SessionManager
import com.hirebeat.com.app.danmon.feature.auth.data.datasource.remote.api.AuthApi
import com.hirebeat.com.app.danmon.feature.auth.data.datasource.remote.model.*
import com.hirebeat.com.app.danmon.feature.auth.domain.entities.AuthToken
import com.hirebeat.com.app.danmon.feature.auth.domain.repositories.AuthRepository
import com.hirebeat.com.app.danmon.feature.auth.data.datasource.remote.mapper.toDomain
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val sessionManager: SessionManager
) : AuthRepository {

    override suspend fun login(request: LoginRequestDto): AuthToken {
        val token = api.login(request).toDomain()
        sessionManager.saveToken(token.token)
        return token
    }

    override suspend fun register(request: RegisterRequestDto): AuthToken {
        val tokenDto = api.register(request)
        val domainToken = tokenDto.toDomain()
        sessionManager.saveToken(domainToken.token)
        return domainToken
    }
}
