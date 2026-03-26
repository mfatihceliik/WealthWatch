package com.example.wealthwatch.di

import android.content.Context
import androidx.room.Room
import com.example.wealthwatch.data.local.WealthWatchDatabase
import com.example.wealthwatch.data.local.dao.AssetDao
import com.example.wealthwatch.data.local.dao.WatchlistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WealthWatchDatabase {
        return Room.databaseBuilder(
            context,
            WealthWatchDatabase::class.java,
            "wealth_watch"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }
    @Provides
    fun provideAssetDao(db: WealthWatchDatabase): AssetDao = db.assetDao()

    @Provides
    fun provideWatchListDao(db: WealthWatchDatabase): WatchlistDao = db.watchlistDao()

    @Provides
    fun provideSearchHistoryDao(db: WealthWatchDatabase): com.example.wealthwatch.data.local.dao.SearchHistoryDao = db.searchHistoryDao()
}