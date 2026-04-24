package com.hirebeat.com.app.danmon.feature.gig_requests.domain.usecases
import com.hirebeat.com.app.danmon.feature.gig_requests.domain.repositories.GigRequestRepository
import javax.inject.Inject

class UpdateGigRequestStatusUseCase @Inject constructor(private val repository: GigRequestRepository) {
    suspend operator fun invoke(id: String, status: String) = repository.updateRequestStatus(id, status)
}