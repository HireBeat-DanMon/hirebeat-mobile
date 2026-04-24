package com.hirebeat.com.app.danmon.feature.profile.domain.entities

data class UserProfile(
    val id: String,
    val photoUrl : String = "",
    val fullName: String,
    val email: String,
    val city: String,
    val experience: Int,
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
enum class SkillLevel(val value: Int, val displayName: String) {
    BASICO(1, "BASICO"),
    PRINCIPIANTE(2, "PRINCIPIANTE"),
    INTERMEDIO(3, "INTERMEDIO"),
    AVANZADO(4, "AVANZADO"),
    PROFESIONAL(5, "PROFESIONAL");

    companion object {
        fun fromString(level: String): SkillLevel {
            return entries.find { it.displayName == level.uppercase() } ?: BASICO
        }

        fun fromInt(value: Int): SkillLevel {
            return entries.find { it.value == value } ?: BASICO
        }
    }
}