package com.example.wealthwatch.domain.use_case.market

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.domain.repository.remote.crypto.CryptoRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCryptoMarketUseCase @Inject constructor(
    private val repository: CryptoRepository
) {
    private var symbolOrder: List<String>? = null

    operator fun invoke(): Flow<Resource<List<MarketAsset>>> {
        return repository.getCryptoTickers().map { resource ->
            if (resource is Resource.Success) {
                val tickerList = resource.data
                if (tickerList.isEmpty()) {
                    resource
                } else {
                    val currentOrder = symbolOrder
                    val sortedList = if (currentOrder == null) {
                        val sorted = tickerList.sortedByDescending { it.volume }
                        symbolOrder = sorted.map { it.symbol }
                        sorted
                    } else {
                        val tickerMap = tickerList.associateBy { it.symbol }
                        currentOrder.mapNotNull { tickerMap[it] }
                    }
                    Resource.Success(sortedList)
                }
            } else {
                resource
            }
        }
    }
}
