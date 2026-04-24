package com.hirebeat.com.app.danmon.feature.review.domain.repositories
import com.hirebeat.com.app.danmon.feature.review.domain.entities.Review
import com.hirebeat.com.app.danmon.feature.review.data.datasource.remote.model.CreateReviewRequestDto
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    suspend fun getProfileReviews(profileId: String): List<Review>
    fun getMyReviews(): Flow<List<Review>>
    suspend fun syncMyReviews()
    suspend fun createReview(request: CreateReviewRequestDto)
}