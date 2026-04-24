package com.hirebeat.com.app.danmon.feature.review.data.repositories

import com.hirebeat.com.app.danmon.feature.review.data.datasource.local.dao.ReviewDao
import com.hirebeat.com.app.danmon.feature.review.data.datasource.remote.api.ReviewApi
import com.hirebeat.com.app.danmon.feature.review.data.datasource.remote.mapper.*
import com.hirebeat.com.app.danmon.feature.review.data.datasource.remote.model.CreateReviewRequestDto
import com.hirebeat.com.app.danmon.feature.review.domain.entities.Review
import com.hirebeat.com.app.danmon.feature.review.domain.repositories.ReviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val api: ReviewApi,
    private val dao: ReviewDao
) : ReviewRepository {

    override suspend fun getProfileReviews(profileId: String): List<Review> {
        return api.getProfileReviews(profileId).map { it.toDomain() }
    }

    override fun getMyReviews(): Flow<List<Review>> = flow {
        val initialData = dao.getAllMyReviews().first()
        emit(initialData.map { it.toDomain() })

        try {
            val remoteReviews = api.getMyReviews()
            dao.insertReviews(remoteReviews.map { it.toEntity() })
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val continuousLocalData = dao.getAllMyReviews().map { entities ->
            entities.map { it.toDomain() }
        }
        emitAll(continuousLocalData)
    }

    override suspend fun syncMyReviews() {
        val remoteReviews = api.getMyReviews()
        dao.insertReviews(remoteReviews.map { it.toEntity() })
    }

    override suspend fun createReview(request: CreateReviewRequestDto) {
        api.createReview(request)
        syncMyReviews()
    }
}