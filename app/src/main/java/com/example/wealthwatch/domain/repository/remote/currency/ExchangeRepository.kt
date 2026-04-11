package com.example.wealthwatch.domain.repository.remote.currency

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.data.remote.model.ExchangeRateResponse
import com.example.wealthwatch.domain.model.asset.MarketAsset
import kotlinx.coroutines.flow.Flow

interface ExchangeRepository {

    fun tickerUpdate(): Flow<SocketState<List<MarketAsset>>>
    fun getExchanges(): Flow<Resource<List<MarketAsset>>>
    fun searchExchanges(query: String): Flow<Resource<List<MarketAsset>>>
    fun getExchangeGainers(): Flow<Resource<List<MarketAsset>>>
    fun getExchangeLosers(): Flow<Resource<List<MarketAsset>>>
    suspend fun fetchAndSaveRates(): Flow<Resource<ExchangeRateResponse>>
}