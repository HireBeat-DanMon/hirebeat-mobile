package com.hirebeat.com.app.danmon.feature.profile.data.di

import com.hirebeat.com.app.danmon.feature.profile.data.datasource.local.ProfileDao
import com.hirebeat.com.app.danmon.feature.profile.data.datasource.remote.api.ProfileApi
import com.hirebeat.com.app.danmon.feature.profile.data.repositories.ProfileRepositoryImpl
import com.hirebeat.com.app.danmon.feature.profile.domain.repositories.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {
    @Provides
    @Singleton
    fun provideProfileApi(retrofit: Retrofit): ProfileApi = retrofit.create(ProfileApi::class.java)

    @Provides
    @Singleton
    fun provideProfileRepository(api: ProfileApi, dao : ProfileDao): ProfileRepository = ProfileRepositoryImpl(api, dao)
}