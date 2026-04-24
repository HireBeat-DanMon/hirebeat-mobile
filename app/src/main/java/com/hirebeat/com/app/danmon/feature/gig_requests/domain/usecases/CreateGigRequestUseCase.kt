package com.hirebeat.com.app.danmon.feature.gig_requests.domain.usecases

import com.hirebeat.com.app.danmon.feature.gig_requests.domain.repositories.GigRequestRepository
import javax.inject.Inject

class CreateGigRequestUseCase @Inject constructor(
    private val repository: GigRequestRepository
) {
    suspend operator fun invoke(
        musicianProfileId: String, startTime: String, endTime: String,
        location: String, paymentOffered: Double, messageDetails: String?
    ): Result<Unit> {
        return repository.createRequest(
            musicianProfileId, startTime, endTime, location, paymentOffered, messageDetails
        )
    }
}