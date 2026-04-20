package com.hirebeat.com.app.danmon.feature.profile.data.datasource.remote.mapper

import com.hirebeat.com.app.danmon.feature.profile.data.datasource.remote.model.SimpleCatalogResponseDto
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.CatalogItem

fun SimpleCatalogResponseDto.toDomain(): CatalogItem {
    return CatalogItem(
        id = this.id,
        name = this.name
    )
}