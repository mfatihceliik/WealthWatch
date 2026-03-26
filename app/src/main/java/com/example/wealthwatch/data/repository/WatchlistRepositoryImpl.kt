package com.example.wealthwatch.data.repository

import com.example.wealthwatch.data.local.LocalDataSource
import com.example.wealthwatch.data.local.entity.watchlist.WatchlistEntity
import com.example.wealthwatch.domain.repository.local.watchlist.WatchlistRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class WatchlistRepositoryImpl @Inject constructor(
    private val local: LocalDataSource
): WatchlistRepository {
    override fun getWatchlist(): Flow<List<WatchlistEntity>> = local.getWatchlist()
    override suspend fun insertWatchlist(entity: WatchlistEntity) = local.insertWatchlist(entity)
    override suspend fun deleteWatchlist(symbol: String) = local.deleteWatchlist(symbol)
    override fun isFavorite(symbol: String): Flow<Boolean> = local.isFavorite(symbol)
}