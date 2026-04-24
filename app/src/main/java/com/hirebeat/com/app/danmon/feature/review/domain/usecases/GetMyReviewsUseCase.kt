package com.hirebeat.com.app.danmon.feature.review.domain.usecases

import com.hirebeat.com.app.danmon.feature.review.domain.entities.Review
import com.hirebeat.com.app.danmon.feature.review.domain.repositories.ReviewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyReviewsUseCase @Inject constructor(
    private val repository: ReviewRepository
) {
    fun execute(): Flow<List<Review>> = repository.getMyReviews()
}