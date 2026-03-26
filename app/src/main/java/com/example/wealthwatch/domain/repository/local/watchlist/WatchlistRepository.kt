package com.example.wealthwatch.domain.repository.local.watchlist

import com.example.wealthwatch.data.local.entity.watchlist.WatchlistEntity
import kotlinx.coroutines.flow.Flow

interface WatchlistRepository {
    fun getWatchlist(): Flow<List<WatchlistEntity>>
    suspend fun insertWatchlist(entity: WatchlistEntity)
    suspend fun deleteWatchlist(symbol: String)
    fun isFavorite(symbol: String): Flow<Boolean>
}
