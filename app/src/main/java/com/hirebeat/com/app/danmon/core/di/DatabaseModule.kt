package com.hirebeat.com.app.danmon.core.di

import android.content.Context
import androidx.room.Room
import com.hirebeat.com.app.danmon.core.database.AppDatabase
import com.hirebeat.com.app.danmon.feature.review.data.datasource.local.dao.ReviewDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideReviewDao(database: AppDatabase): ReviewDao {
        return database.reviewDao()
    }
}