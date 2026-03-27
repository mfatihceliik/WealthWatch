package com.example.wealthwatch.data.remote.currency

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.data.remote.model.CurrencyRateResponse
import kotlinx.coroutines.flow.Flow

interface CurrencyDataSource {
    fun currencyTickerUpdate(): Flow<SocketState<List<MarketAsset>>>
    fun getCurrencies(): Flow<Resource<List<MarketAsset>>>
    fun searchCurrencies(query: String): Flow<Resource<List<MarketAsset>>>
    fun getCurrencyRates(): Flow<Resource<CurrencyRateResponse>>
    fun getCurrencyGainers(): Flow<Resource<List<MarketAsset>>>
    fun getCurrencyLosers(): Flow<Resource<List<MarketAsset>>>
}