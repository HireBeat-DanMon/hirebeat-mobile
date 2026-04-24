// danmon/feature/gig_requests/data/datasource/remote/model/GigRequestDTO.kt
package com.hirebeat.com.app.danmon.feature.gig_requests.data.datasource.remote.model

import com.hirebeat.com.app.danmon.feature.gig_requests.domain.entities.GigRequestItem
import kotlinx.serialization.Serializable

@Serializable
data class GigRequestResponseDto(
    val id: String,
    val recruiterId: String,
    val musicianProfileId: String,
    val startTime: String,
    val endTime: String,
    val location: String,
    val paymentOffered: Double,
    val messageDetails: String? = null,
    val status: String,
    val createdAt: String? = null
) {
    fun toDomain() = GigRequestItem(
        id = id,
        recruiterId = recruiterId,
        musicianProfileId = musicianProfileId,
        startTime = startTime,
        endTime = endTime,
        location = location,
        paymentOffered = paymentOffered,
        messageDetails = messageDetails,
        status = status
    )
}

@Serializable
data class UpdateStatusDto(
    val status: String
)
@Serializable
data class CreateGigRequestDto(
    val musicianProfileId: String,
    val startTime: String,
    val endTime: String,
    val location: String,
    val paymentOffered: Double,
    val messageDetails: String?
)

@Serializable
data class CreateGigRequestResponse(
    val id: String,
    val message: String
)