package com.example.wealthwatch.domain.use_case.market

import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.domain.model.crypto.Crypto
import com.example.wealthwatch.domain.repository.remote.crypto.CryptoRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

class GetCoinDetailStreamUseCase @Inject constructor(
    private val repository: CryptoRepository
) {
    operator fun invoke(symbol: String): Flow<Crypto> =
        repository.tickerUpdate().mapNotNull { state ->
            if (state is SocketState.Connected) {
                state.data.find { it.symbol == symbol }
            } else {
                null
            }
        }
}
