package com.hirebeat.com.app.danmon.feature.review.data.datasource.local.dao

import androidx.room.*
import com.hirebeat.com.app.danmon.feature.review.data.datasource.local.entity.ReviewEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {
    @Query("SELECT * FROM reviews")
    fun getAllMyReviews(): Flow<List<ReviewEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviews(reviews: List<ReviewEntity>)
}