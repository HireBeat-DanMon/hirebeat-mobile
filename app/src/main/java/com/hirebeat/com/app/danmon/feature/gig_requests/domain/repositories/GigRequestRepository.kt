package com.hirebeat.com.app.danmon.feature.gig_requests.domain.repositories

import com.hirebeat.com.app.danmon.feature.gig_requests.domain.entities.GigRequestItem

interface GigRequestRepository {
    suspend fun createRequest(
        musicianProfileId: String,
        startTime: String,
        endTime: String,
        location: String,
        paymentOffered: Double,
        messageDetails: String?
    ): Result<Unit>
    suspend fun getRecruiterRequests(): Result<List<GigRequestItem>>
    suspend fun getMusicianRequests(): Result<List<GigRequestItem>>
    suspend fun updateRequestStatus(id: String, status: String): Result<Unit>
}