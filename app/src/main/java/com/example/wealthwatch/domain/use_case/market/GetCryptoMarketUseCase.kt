package com.example.wealthwatch.domain.use_case.market

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.domain.model.crypto.Crypto
import com.example.wealthwatch.domain.repository.remote.crypto.CryptoRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCryptoMarketUseCase @Inject constructor(
    private val repository: CryptoRepository
) {
    operator fun invoke(): Flow<Resource<List<Crypto>>> {
        var symbolOrder: List<String>? = null
        return repository.getCryptoTickers().map { resource ->
            if (resource is Resource.Success) {
                val tickerList = resource.data
                if (tickerList.isEmpty()) {
                    resource
                } else {
                    val sortedList = if (symbolOrder == null) {
                        val sorted = tickerList.sortedByDescending { 
                            it.quoteVolume.toDoubleOrNull() ?: 0.0 
                        }
                        symbolOrder = sorted.map { it.symbol }
                        sorted
                    } else {
                        val tickerMap = tickerList.associateBy { it.symbol }
                        symbolOrder.mapNotNull { tickerMap[it] }
                    }
                    Resource.Success(sortedList)
                }
            } else {
                resource
            }
        }
    }
}
