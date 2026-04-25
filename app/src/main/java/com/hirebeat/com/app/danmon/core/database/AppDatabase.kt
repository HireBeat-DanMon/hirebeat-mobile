package com.hirebeat.com.app.danmon.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hirebeat.com.app.danmon.feature.review.data.datasource.local.dao.ReviewDao
import com.hirebeat.com.app.danmon.feature.review.data.datasource.local.entity.ReviewEntity
import com.hirebeat.com.app.danmon.feature.profile.data.datasource.local.ProfileDao
import com.hirebeat.com.app.danmon.feature.profile.data.datasource.local.entity.UserProfileEntity
import com.hirebeat.com.app.danmon.feature.profile.data.datasource.local.Converters

@Database(
    entities = [
        ReviewEntity::class,
        UserProfileEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun reviewDao(): ReviewDao

    abstract fun profileDao(): ProfileDao
}