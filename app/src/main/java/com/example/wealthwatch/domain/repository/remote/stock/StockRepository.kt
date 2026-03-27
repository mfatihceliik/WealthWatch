package com.example.wealthwatch.domain.repository.remote.stock

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.domain.model.asset.MarketAsset
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    fun stockTickerUpdate(): Flow<SocketState<List<MarketAsset>>>
    fun getUsStocks(): Flow<Resource<List<MarketAsset>>>
    fun getBistStocks(): Flow<Resource<List<MarketAsset>>>
    fun getBistGainers(): Flow<Resource<List<MarketAsset>>>
    fun getBistLosers(): Flow<Resource<List<MarketAsset>>>
    fun getUsGainers(): Flow<Resource<List<MarketAsset>>>
    fun getUsLosers(): Flow<Resource<List<MarketAsset>>>
    fun searchStocks(query: String): Flow<Resource<List<MarketAsset>>>
    fun searchUsStocks(query: String): Flow<Resource<List<MarketAsset>>>
    fun searchBistStocks(query: String): Flow<Resource<List<MarketAsset>>>
}
