package com.hirebeat.com.app.danmon.feature.profile.domain.entities

data class UserProfile(
    val id: String,
    val fullName: String,
    val email: String,
    val city: String,
    val description: String,
    val role: String,
    val instruments: List<ProfileInstrument> = emptyList(),
    val genres: List<CatalogItem> = emptyList(),
    val links: List<ProfileLink> = emptyList()
)

data class ProfileLink(
    val name: String,
    val ref: String
)