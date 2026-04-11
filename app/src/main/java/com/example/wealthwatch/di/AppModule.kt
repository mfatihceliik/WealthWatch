package com.example.wealthwatch.di

import com.example.wealthwatch.core.provider.AppInfoProvider
import com.example.wealthwatch.data.provider.WealthWatchInfoProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindAppInfoProvider(
        wealthWatchInfoProvider: WealthWatchInfoProvider
    ): AppInfoProvider
}
