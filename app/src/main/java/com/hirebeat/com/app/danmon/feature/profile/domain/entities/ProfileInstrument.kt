package com.hirebeat.com.app.danmon.feature.profile.domain.entities

data class ProfileInstrument(
    val instrument: CatalogItem,
    val level: String,
    val isPrincipal: Boolean = false
)

