package com.example.wealthwatch.domain.use_case.market

import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.domain.repository.remote.crypto.CryptoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMarketStreamUseCase @Inject constructor(
    private val repository: CryptoRepository
) {
    operator fun invoke(): Flow<SocketState<List<MarketAsset>>> = repository.tickerUpdate()
}
