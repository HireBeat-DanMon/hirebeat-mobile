package com.hirebeat.com.app.danmon.feature.auth.data.di

import com.hirebeat.com.app.danmon.core.data.SessionManager
import com.hirebeat.com.app.danmon.feature.auth.data.datasource.remote.api.AuthApi
import com.hirebeat.com.app.danmon.feature.auth.data.repositories.AuthRepositoryImpl
import com.hirebeat.com.app.danmon.feature.auth.domain.repositories.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideHireBeatApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApi,
        sessionManager: SessionManager
    ): AuthRepository {
        return AuthRepositoryImpl(api, sessionManager)
    }
}