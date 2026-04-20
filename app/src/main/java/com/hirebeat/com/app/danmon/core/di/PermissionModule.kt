package com.hirebeat.com.app.danmon.core.di

import com.hirebeat.com.app.danmon.core.permission.data.AndroidPermissionChecker
import com.hirebeat.com.app.danmon.core.permission.domain.PermissionChecker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PermissionModule {

    @Binds
    @Singleton
    abstract fun bindPermissionChecker(
        impl: AndroidPermissionChecker
    ): PermissionChecker
}