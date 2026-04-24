package com.hirebeat.com.app.danmon.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hirebeat.com.app.danmon.feature.review.data.datasource.local.dao.ReviewDao
import com.hirebeat.com.app.danmon.feature.review.data.datasource.local.entity.ReviewEntity

@Database(
    entities = [
        ReviewEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun reviewDao(): ReviewDao
}