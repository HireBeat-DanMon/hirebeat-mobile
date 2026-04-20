package com.hirebeat.com.app.danmon.core.di

import com.hirebeat.com.app.danmon.core.hardware.data.*
import com.hirebeat.com.app.danmon.core.hardware.domain.*

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HardwareModule {
    @Binds
    @Singleton
    abstract fun bindFlashManager(impl: AndroidFlashManager): FlashManager

    @Binds
    @Singleton
    abstract fun bindFusedLocationManager(impl: FusedLocationRepository): LocationRepository

    @Binds
    @Singleton
    abstract fun bindVibrationManager(impl: AndroidVibrateManager): VibrateManager
}