package com.example.wealthwatch.di

import com.example.wealthwatch.core.util.WealthWatchFormatter
import com.example.wealthwatch.core.util.WealthWatchFormatterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FormatterModule {

    @Binds
    @Singleton
    abstract fun bindWealthWatchFormatter(
        wealthWatchFormatterImpl: WealthWatchFormatterImpl
    ): WealthWatchFormatter
}
