package com.hirebeat.com.app.danmon.feature.review.data.datasource.remote.mapper

import com.hirebeat.com.app.danmon.feature.review.data.datasource.local.entity.ReviewEntity
import com.hirebeat.com.app.danmon.feature.review.data.datasource.remote.model.ReviewResponseDto
import com.hirebeat.com.app.danmon.feature.review.domain.entities.Review

fun ReviewResponseDto.toDomain() = Review(id, reviewerName, rating, comment, createdAt)

fun ReviewResponseDto.toEntity() = ReviewEntity(
    id = this.id,
    reviewerName = this.reviewerName,
    rating = this.rating,
    comment = this.comment,
    createdAt = this.createdAt,
    profileId = "my_own_profile"
)

fun ReviewEntity.toDomain() = Review(
    id = this.id,
    reviewerName = this.reviewerName,
    rating = this.rating,
    comment = this.comment,
    createdAt = this.createdAt
)