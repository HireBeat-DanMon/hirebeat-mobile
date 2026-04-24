package com.hirebeat.com.app.danmon.feature.gig_requests.domain.usecases
import com.hirebeat.com.app.danmon.feature.gig_requests.domain.repositories.GigRequestRepository
import javax.inject.Inject

class GetMusicianRequestsUseCase @Inject constructor(private val repository: GigRequestRepository) {
    suspend operator fun invoke() = repository.getMusicianRequests()
}