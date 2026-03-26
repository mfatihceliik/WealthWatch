package com.example.wealthwatch.domain.use_case.watchlist

import com.example.wealthwatch.data.local.entity.watchlist.WatchlistEntity
import com.example.wealthwatch.domain.repository.local.watchlist.WatchlistRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetWatchlistUseCase @Inject constructor(
    private val repository: WatchlistRepository
) {
    operator fun invoke(): Flow<List<WatchlistEntity>> = repository.getWatchlist()
}
