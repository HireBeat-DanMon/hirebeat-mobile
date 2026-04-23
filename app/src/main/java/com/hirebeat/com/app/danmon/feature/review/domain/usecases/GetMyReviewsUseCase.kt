package com.hirebeat.com.app.danmon.feature.review.domain.usecases

import com.hirebeat.com.app.danmon.feature.review.domain.entities.Review
import com.hirebeat.com.app.danmon.feature.review.domain.repositories.ReviewRepository
import javax.inject.Inject

class GetMyReviewsUseCase @Inject constructor(
    private val repository: ReviewRepository
) {
    suspend fun execute(): List<Review> {
        return repository.getMyReviews()
    }
}