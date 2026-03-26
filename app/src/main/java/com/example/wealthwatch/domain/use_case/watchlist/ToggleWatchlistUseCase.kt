package com.example.wealthwatch.domain.use_case.watchlist

import com.example.wealthwatch.data.local.entity.watchlist.WatchlistEntity
import com.example.wealthwatch.domain.repository.local.watchlist.WatchlistRepository
import com.example.wealthwatch.presentation.model.AssetUiModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first

class ToggleWatchlistUseCase @Inject constructor(
    private val repository: WatchlistRepository
) {
    suspend operator fun invoke(asset: AssetUiModel): Boolean {
        val isFav = repository.isFavorite(asset.symbol).first()
        if (isFav) {
            repository.deleteWatchlist(asset.symbol)
            return false // Removed
        } else {
            val symbol = asset.symbol
            val quote = if (symbol.endsWith("USDT")) "USDT" else "USD"
            symbol.removeSuffix(quote)

            val entity = WatchlistEntity(
                symbol = asset.symbol,
                name = asset.name,
                assetType = asset.type,
                iconUrl = asset.icon,
                price = asset.price,
                volume = asset.volume,
                priceChangePercent = asset.priceChange
            )
            repository.insertWatchlist(entity)
            return true // Added
        }
    }
}
