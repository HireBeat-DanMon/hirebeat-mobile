package com.hirebeat.com.app.danmon.core.hardware.domain

import com.hirebeat.com.app.danmon.core.hardware.domain.model.LocationPoint
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getLocationFlow(interval: Long): Flow<LocationPoint>
}