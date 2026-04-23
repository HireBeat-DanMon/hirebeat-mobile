package com.hirebeat.com.app.danmon.feature.review.domain.entities

data class Review(
    val id: String,
    val reviewerName: String,
    val rating: Int,
    val comment: String,
    val createdAt: String
)