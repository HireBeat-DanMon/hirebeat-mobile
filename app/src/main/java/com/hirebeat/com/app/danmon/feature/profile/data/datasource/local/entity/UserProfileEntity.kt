package com.hirebeat.com.app.danmon.feature.profile.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.CatalogItem
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.ProfileInstrument
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.ProfileLink
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.UserProfile

@Entity(tableName = "my_profile")
data class UserProfileEntity(
    @PrimaryKey val id: String,
    val realId: String,
    val photoUrl: String,
    val fullName: String,
    val email: String,
    val city: String,
    val experience: Int,
    val description: String,
    val role: String,
    val instruments: List<ProfileInstrument>,
    val genres: List<CatalogItem>,
    val links: List<ProfileLink>
)

fun UserProfile.toEntity(): UserProfileEntity {
    return UserProfileEntity(
        id = "ME",
        realId = this.id,
        photoUrl = this.photoUrl,
        fullName = this.fullName,
        email = this.email,
        city = this.city,
        experience = this.experience,
        description = this.description,
        role = this.role,
        instruments = this.instruments,
        genres = this.genres,
        links = this.links
    )
}

fun UserProfileEntity.toDomain(): UserProfile {
    return UserProfile(
        id = this.realId,
        photoUrl = this.photoUrl,
        fullName = this.fullName,
        email = this.email,
        city = this.city,
        experience = this.experience,
        description = this.description,
        role = this.role,
        instruments = this.instruments,
        genres = this.genres,
        links = this.links
    )
}