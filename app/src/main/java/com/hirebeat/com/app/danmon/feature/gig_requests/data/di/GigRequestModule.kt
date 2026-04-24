package com.hirebeat.com.app.danmon.feature.gig_requests.data.di

import com.hirebeat.com.app.danmon.feature.gig_requests.data.datasource.remote.api.GigRequestApi
import com.hirebeat.com.app.danmon.feature.gig_requests.data.repositories.GigRequestRepositoryImpl
import com.hirebeat.com.app.danmon.feature.gig_requests.domain.repositories.GigRequestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GigRequestModule {

    @Provides
    @Singleton
    fun provideGigRequestApi(retrofit: Retrofit): GigRequestApi {
        return retrofit.create(GigRequestApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGigRequestRepository(api: GigRequestApi): GigRequestRepository {
        return GigRequestRepositoryImpl(api)
    }
}