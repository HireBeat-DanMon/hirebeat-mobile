package com.hirebeat.com.app.danmon.feature.review.data.di

import com.hirebeat.com.app.danmon.feature.review.data.datasource.remote.api.ReviewApi
import com.hirebeat.com.app.danmon.feature.review.data.repositories.ReviewRepositoryImpl
import com.hirebeat.com.app.danmon.feature.review.domain.repositories.ReviewRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReviewModule {

    @Provides
    @Singleton
    fun provideReviewApi(retrofit: Retrofit): ReviewApi {
        return retrofit.create(ReviewApi::class.java)
    }

    @Provides
    @Singleton
    fun provideReviewRepository(api: ReviewApi): ReviewRepository {
        return ReviewRepositoryImpl(api)
    }
}