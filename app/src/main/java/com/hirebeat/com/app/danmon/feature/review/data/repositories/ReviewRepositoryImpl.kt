package com.hirebeat.com.app.danmon.feature.review.data.repositories
import com.hirebeat.com.app.danmon.feature.review.data.datasource.remote.api.ReviewApi
import com.hirebeat.com.app.danmon.feature.review.data.datasource.remote.model.CreateReviewRequestDto
import com.hirebeat.com.app.danmon.feature.review.domain.entities.Review
import com.hirebeat.com.app.danmon.feature.review.domain.repositories.ReviewRepository
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val api: ReviewApi
) : ReviewRepository {
    override suspend fun getProfileReviews(profileId: String): List<Review> {
        return api.getProfileReviews(profileId).map {
            Review(it.id, it.reviewerName, it.rating, it.comment, it.createdAt)
        }
    }

    override suspend fun createReview(request: CreateReviewRequestDto): String {
        return api.createReview(request).id
    }

    override suspend fun getMyReviews(): List<Review> {
        return api.getMyReviews().map {
            Review(it.id, it.reviewerName, it.rating, it.comment, it.createdAt)
        }
    }
}