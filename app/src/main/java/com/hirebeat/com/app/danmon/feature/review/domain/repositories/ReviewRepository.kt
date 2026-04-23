package com.hirebeat.com.app.danmon.feature.review.domain.repositories
import com.hirebeat.com.app.danmon.feature.review.domain.entities.Review
import com.hirebeat.com.app.danmon.feature.review.data.datasource.remote.model.CreateReviewRequestDto

interface ReviewRepository {
    suspend fun getMyReviews(): List<Review>
    suspend fun getProfileReviews(profileId: String): List<Review>
    suspend fun createReview(request: CreateReviewRequestDto): String
}