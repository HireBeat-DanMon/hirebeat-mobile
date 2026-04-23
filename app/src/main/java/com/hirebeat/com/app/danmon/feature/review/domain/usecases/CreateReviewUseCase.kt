package com.hirebeat.com.app.danmon.feature.review.domain.usecases

import com.hirebeat.com.app.danmon.feature.review.data.datasource.remote.model.CreateReviewRequestDto
import com.hirebeat.com.app.danmon.feature.review.domain.repositories.ReviewRepository
import javax.inject.Inject

class CreateReviewUseCase @Inject constructor(
    private val repository: ReviewRepository
) {
    suspend fun execute(request: CreateReviewRequestDto): String {
        return repository.createReview(request)
    }
}