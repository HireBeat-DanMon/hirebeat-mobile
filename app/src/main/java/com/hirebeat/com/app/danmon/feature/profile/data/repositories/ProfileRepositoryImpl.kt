package com.hirebeat.com.app.danmon.feature.profile.data.repositories

import com.hirebeat.com.app.danmon.feature.profile.data.datasource.local.ProfileDao
import com.hirebeat.com.app.danmon.feature.profile.data.datasource.local.entity.toDomain
import com.hirebeat.com.app.danmon.feature.profile.data.datasource.local.entity.toEntity
import com.hirebeat.com.app.danmon.feature.profile.data.datasource.remote.api.ProfileApi
import com.hirebeat.com.app.danmon.feature.profile.data.datasource.remote.mapper.*
import com.hirebeat.com.app.danmon.feature.profile.data.datasource.remote.model.ProfileSetupRequestDto
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.*
import com.hirebeat.com.app.danmon.feature.profile.domain.repositories.ProfileRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: ProfileApi,
    private val dao : ProfileDao
) : ProfileRepository {

    override suspend fun getMyProfile(): UserProfile {
        return try {
            val remoteProfile = api.getMyProfile().toDomain()
            dao.insertMyProfile(remoteProfile.toEntity())

            remoteProfile
        } catch (e: Exception) {
            val localProfile = dao.getMyProfile()
            localProfile?.toDomain() ?: throw Exception("Sin conexión a internet y sin perfil guardado", e)
        }
    }

    override suspend fun getAllProfiles(): List<UserProfile> {
        return api.getAllProfiles().map { it.toDomain() }
    }

    override suspend fun getProfileById(id: String): UserProfile {
        return api.getProfileById(id).toDomain()
    }

    override suspend fun updateProfile(request: ProfileSetupRequestDto): UserProfile {
        return api.updateProfile(request).toDomain()
    }

    override suspend fun uploadImage(fileBytes: ByteArray, fileName: String): String {
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), fileBytes)
        val body = MultipartBody.Part.createFormData("image", fileName, requestFile)
        return api.uploadProfileImage(body).url
    }

    override suspend fun getInstruments(): List<CatalogItem> {
        return api.getInstruments().map { it.toDomain() }
    }

    override suspend fun getGenres(): List<CatalogItem> {
        return api.getGenres().map { it.toDomain() }
    }
}