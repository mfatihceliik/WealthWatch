package com.example.wealthwatch.domain.repository.remote.currency

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.data.remote.model.CurrencyRateResponse
import com.example.wealthwatch.domain.model.asset.MarketAsset
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    fun tickerUpdate(): Flow<SocketState<List<MarketAsset>>>
    fun getCurrencies(): Flow<Resource<List<MarketAsset>>>
    fun searchCurrencies(query: String): Flow<Resource<List<MarketAsset>>>
    fun getCurrencyGainers(): Flow<Resource<List<MarketAsset>>>
    fun getCurrencyLosers(): Flow<Resource<List<MarketAsset>>>
    suspend fun fetchAndSaveRates(): Flow<Resource<CurrencyRateResponse>>
}