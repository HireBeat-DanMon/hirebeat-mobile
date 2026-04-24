package com.hirebeat.com.app.danmon.feature.profile.data.datasource.remote.mapper

import com.hirebeat.com.app.danmon.feature.profile.data.datasource.remote.model.ProfileResponseDto
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.*

fun ProfileResponseDto.toDomain(): UserProfile {
    return UserProfile(
        id = this.id ?: "",
        fullName = this.fullname ?: "Usuario",
        photoUrl = this.imageUrl ?: "",
        email = this.email ?: "",
        city = this.city ?: "Sin ubicación",
        experience = this.experience ?: 0,
        description = this.descripcion ?: "",
        role = if (!instruments.isNullOrEmpty()) "Musician" else "Recruiter",
        instruments = this.instruments?.map {
            ProfileInstrument(
                instrument = CatalogItem(it.id ?: 0, it.name ?: ""),
                level = it.level ?: "BASICO",
                isPrincipal = it.isPrincipal ?: false
            )
        } ?: emptyList(),
        genres = this.genres?.map {
            CatalogItem(it.id ?: 0, it.name ?: "")
        } ?: emptyList(),
        links = this.links?.map {
            ProfileLink(
                name = it.name ?: "",
                ref = it.ref ?: ""
            )
        } ?: emptyList()
    )
}