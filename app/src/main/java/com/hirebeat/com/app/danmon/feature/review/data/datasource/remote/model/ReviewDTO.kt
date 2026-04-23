package com.hirebeat.com.app.danmon.feature.review.data.datasource.remote.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewResponseDto(
    @SerializedName("id") val id: String,
    @SerializedName("reviewerName") val reviewerName: String,
    @SerializedName("rating") val rating: Int,
    @SerializedName("comment") val comment: String,
    @SerializedName("createdAt") val createdAt: String
)

@Serializable
data class CreateReviewRequestDto(
    @SerializedName("musicianProfileId") val musicianProfileId: String,
    @SerializedName("rating") val rating: Int,
    @SerializedName("comment") val comment: String
)

@Serializable
data class CreateReviewResultDto(
    @SerializedName("id") val id: String,
    @SerializedName("message") val message: String
)