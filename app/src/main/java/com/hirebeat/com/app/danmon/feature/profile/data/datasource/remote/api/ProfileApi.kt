package com.hirebeat.com.app.danmon.feature.profile.data.datasource.remote.api

import com.hirebeat.com.app.danmon.feature.profile.data.datasource.remote.model.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface ProfileApi {
    @GET("/profile/me")
    suspend fun getMyProfile(): ProfileResponseDto

    @Multipart
    @POST("/profile/image")
    suspend fun uploadProfileImage(
        @Part image: MultipartBody.Part
    ): ImageResponseDto
    @PUT("/profile")
    suspend fun updateProfile(@Body request: ProfileSetupRequestDto): ProfileResponseDto

    @GET("/profile")
    suspend fun getAllProfiles(): List<ProfileResponseDto>

    @GET("/instruments")
    suspend fun getInstruments(): List<SimpleCatalogResponseDto>

    @GET("/genre")
    suspend fun getGenres(): List<SimpleCatalogResponseDto>
}