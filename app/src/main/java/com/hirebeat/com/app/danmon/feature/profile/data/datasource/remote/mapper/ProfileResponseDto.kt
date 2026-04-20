package com.hirebeat.com.app.danmon.feature.profile.data.datasource.remote.mapper

import com.hirebeat.com.app.danmon.feature.profile.data.datasource.remote.model.ProfileResponseDto
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.*

fun ProfileResponseDto.toDomain(): UserProfile {
    return UserProfile(
        id = this.id ?: "",
        fullName = this.fullname ?: "Usuario",
        email = this.email ?: "",
        city = this.city ?: "Sin ubicación",
        description = this.descripcion ?: "",
        role = if (!instruments.isNullOrEmpty()) "Musician" else "Recruiter",

        // Mapeamos los instrumentos (level ya es String en el DTO y en el Dominio)
        instruments = this.instruments?.map {
            ProfileInstrument(
                instrument = CatalogItem(it.id ?: 0, it.name ?: ""),
                level = it.level ?: "BASICO", // Ahora coinciden los tipos
                isPrincipal = it.isPrincipal ?: false
            )
        } ?: emptyList(),

        // Mapeamos los géneros
        genres = this.genres?.map {
            CatalogItem(it.id ?: 0, it.name ?: "")
        } ?: emptyList(),

        // Mapeamos los links
        links = this.links?.map {
            ProfileLink(
                name = it.name ?: "",
                ref = it.ref ?: ""
            )
        } ?: emptyList()
    )
}