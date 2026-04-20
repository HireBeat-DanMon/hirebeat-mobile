package com.hirebeat.com.app.danmon.feature.auth.data.datasource.remote.mapper

import com.hirebeat.com.app.danmon.feature.auth.data.datasource.remote.model.AuthResponseDto
import com.hirebeat.com.app.danmon.feature.auth.domain.entities.AuthToken

fun AuthResponseDto.toDomain(): AuthToken {
    return AuthToken(
        token = this.token
    )
}