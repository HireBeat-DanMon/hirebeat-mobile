package com.hirebeat.com.app.danmon.feature.profile.data.datasource.remote.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SimpleCatalogResponseDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)