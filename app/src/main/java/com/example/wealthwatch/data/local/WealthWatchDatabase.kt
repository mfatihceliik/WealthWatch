package com.example.wealthwatch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.wealthwatch.data.local.converter.AssetConverters
import com.example.wealthwatch.data.local.dao.AssetDao
import com.example.wealthwatch.data.local.dao.SearchHistoryDao
import com.example.wealthwatch.data.local.dao.WatchlistDao
import com.example.wealthwatch.data.local.entity.watchlist.WatchlistEntity
import com.example.wealthwatch.data.local.entity.asset.AssetEntity
import com.example.wealthwatch.data.local.entity.search.SearchHistoryEntity
import com.example.wealthwatch.data.local.entity.transaction.TransactionEntity

@Database(
    entities = [
        AssetEntity::class, 
        TransactionEntity::class, 
        WatchlistEntity::class, 
        SearchHistoryEntity::class
    ], 
    version = 14,
    exportSchema = false
)
@TypeConverters(AssetConverters::class)
abstract class WealthWatchDatabase : RoomDatabase() {
    abstract fun assetDao(): AssetDao
    abstract fun watchlistDao(): WatchlistDao
    abstract fun searchHistoryDao(): SearchHistoryDao
}
