package com.hirebeat.com.app.danmon.feature.review.data.datasource.remote.mapper

import com.hirebeat.com.app.danmon.feature.review.data.datasource.local.entity.ReviewEntity
import com.hirebeat.com.app.danmon.feature.review.data.datasource.remote.model.ReviewResponseDto
import com.hirebeat.com.app.danmon.feature.review.domain.entities.Review

fun ReviewResponseDto.toDomain() = Review(id, reviewerName, rating, comment, createdAt)

fun ReviewResponseDto.toEntity(profileId: String) = ReviewEntity(
    id = id,
    reviewerName = reviewerName,
    rating = rating,
    comment = comment,
    createdAt = createdAt,
    profileId = profileId
)
fun ReviewEntity.toDomain() = Review(id, reviewerName, rating, comment, createdAt)