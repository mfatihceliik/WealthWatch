package com.example.wealthwatch.domain.use_case.watchlist

import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.domain.repository.local.watchlist.WatchlistRepository
import com.example.wealthwatch.domain.repository.remote.crypto.CryptoRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class GetWatchlistStreamUseCase @Inject constructor(
    private val cryptoRepository: CryptoRepository,
    private val repository: WatchlistRepository
) {
    operator fun invoke(): Flow<List<MarketAsset>> {
        val watchlistFlow = repository.getWatchlist()

        val marketMapFlow = cryptoRepository.tickerUpdate().map { state ->
            if (state is SocketState.Connected) {
                state.data.associateBy { it.symbol }
            } else {
                emptyMap<String, MarketAsset>()
            }
        }

        return combine(watchlistFlow, marketMapFlow) { watchlist, marketMap ->
            watchlist.map { entity ->
                val liveData = marketMap[entity.symbol]

                MarketAsset(
                    symbol = entity.symbol,
                    name = entity.name,
                    icon = entity.iconUrl ?: "",
                    type = AssetType.fromCode(entity.assetType),
                    currentPrice = liveData?.currentPrice ?: entity.price.toDoubleOrNull() ?: 0.0,
                    priceChange = liveData?.priceChange ?: 0.0,
                    priceChangePercent = liveData?.priceChangePercent ?: entity.priceChangePercent.toDoubleOrNull() ?: 0.0,
                    volume = liveData?.volume ?: 0.0
                )
            }
        }
    }
}
