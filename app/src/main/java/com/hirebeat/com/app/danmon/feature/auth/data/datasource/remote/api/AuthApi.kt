package com.hirebeat.com.app.danmon.feature.auth.data.datasource.remote.api

import com.hirebeat.com.app.danmon.feature.auth.data.datasource.remote.model.AuthResponseDto
import com.hirebeat.com.app.danmon.feature.auth.data.datasource.remote.model.LoginRequestDto
import com.hirebeat.com.app.danmon.feature.auth.data.datasource.remote.model.RegisterRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequestDto): AuthResponseDto

    @POST("/auth/register")
    suspend fun register(@Body request: RegisterRequestDto): AuthResponseDto
}