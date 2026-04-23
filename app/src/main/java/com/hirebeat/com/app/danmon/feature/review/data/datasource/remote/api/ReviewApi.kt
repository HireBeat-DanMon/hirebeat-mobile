package com.hirebeat.com.app.danmon.feature.review.data.datasource.remote.api

import com.hirebeat.com.app.danmon.feature.review.data.datasource.remote.model.*
import retrofit2.http.*

interface ReviewApi {
    @GET("/reviews/profile/{profileId}")
    suspend fun getProfileReviews(@Path("profileId") profileId: String): List<ReviewResponseDto>

    @GET("/reviews/me")
    suspend fun getMyReviews(): List<ReviewResponseDto>
    @POST("/reviews")
    suspend fun createReview(@Body request: CreateReviewRequestDto): CreateReviewResultDto
}