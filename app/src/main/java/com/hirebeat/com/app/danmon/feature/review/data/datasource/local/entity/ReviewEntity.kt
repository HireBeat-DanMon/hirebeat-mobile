package com.hirebeat.com.app.danmon.feature.review.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class ReviewEntity(
    @PrimaryKey val id: String,
    val reviewerName: String,
    val rating: Int,
    val comment: String,
    val createdAt: String,
    val profileId: String
)