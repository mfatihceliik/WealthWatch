package com.example.wealthwatch.data.remote.currency

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.data.remote.model.ExchangeRateResponse
import kotlinx.coroutines.flow.Flow

interface ExchangeDataSource {
    fun exchangeTickerUpdate(): Flow<SocketState<List<MarketAsset>>>
    fun getExchange(): Flow<Resource<List<MarketAsset>>>
    fun searchExchanges(query: String): Flow<Resource<List<MarketAsset>>>
    fun getExchangeRates(): Flow<Resource<ExchangeRateResponse>>
    fun getExchangeGainers(): Flow<Resource<List<MarketAsset>>>
    fun getExchangeLosers(): Flow<Resource<List<MarketAsset>>>
}