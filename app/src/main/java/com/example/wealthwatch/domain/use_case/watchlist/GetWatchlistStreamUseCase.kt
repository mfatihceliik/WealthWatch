package com.example.wealthwatch.domain.use_case.watchlist

import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.domain.model.crypto.Crypto
import com.example.wealthwatch.domain.repository.local.watchlist.WatchlistRepository
import com.example.wealthwatch.domain.repository.remote.crypto.CryptoRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class GetWatchlistStreamUseCase @Inject constructor(
    private val cryptoRepository: CryptoRepository,
    private val repository: WatchlistRepository
) {
    operator fun invoke(): Flow<List<Crypto>> {
        val watchlistFlow = repository.getWatchlist()

        val marketMapFlow = cryptoRepository.tickerUpdate().map { state ->
            if (state is SocketState.Connected) {
                state.data.associateBy { it.symbol }
            } else {
                emptyMap()
            }
        }

        return combine(watchlistFlow, marketMapFlow) { watchlist, marketMap ->
            watchlist.map { entity ->
                val liveData = marketMap[entity.symbol]

                Crypto(
                    symbol = entity.symbol,
                    name = entity.name,
                    priceChange = liveData?.priceChange ?: "0.0",
                    priceChangePercent = liveData?.priceChangePercent ?: entity.priceChangePercent,
                    price = liveData?.price ?: entity.price.toDoubleOrNull() ?: 0.0,
                    volume = liveData?.volume ?: "0.0",
                    quoteVolume = liveData?.quoteVolume ?: entity.volume,
                    iconUrl = entity.iconUrl,
                    type = com.example.wealthwatch.domain.model.asset.AssetType.CRYPTO
                )
            }
        }
    }
}
