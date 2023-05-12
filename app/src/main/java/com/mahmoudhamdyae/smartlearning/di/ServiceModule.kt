package com.mahmoudhamdyae.smartlearning.di

import com.mahmoudhamdyae.smartlearning.base.LogService
import com.mahmoudhamdyae.smartlearning.base.LogServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun provideLogService(impl: LogServiceImpl): LogService
}