package com.hirebeat.com.app.danmon.feature.profile.data.datasource.remote.api

import com.hirebeat.com.app.danmon.feature.profile.data.datasource.remote.model.*
import retrofit2.http.*

interface ProfileApi {
    @GET("/profile/me")
    suspend fun getMyProfile(): ProfileResponseDto

    @PUT("/profile")
    suspend fun updateProfile(@Body request: ProfileSetupRequestDto): ProfileResponseDto

    @GET("/instruments")
    suspend fun getInstruments(): List<SimpleCatalogResponseDto>

    @GET("/genre")
    suspend fun getGenres(): List<SimpleCatalogResponseDto>
}