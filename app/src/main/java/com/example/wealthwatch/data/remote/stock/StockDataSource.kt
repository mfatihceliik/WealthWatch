package com.example.wealthwatch.data.remote.stock

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.domain.model.stock.Stock
import kotlinx.coroutines.flow.Flow

interface StockDataSource {
    fun stockTickerUpdate(): Flow<SocketState<List<Stock>>>
    fun getUsStocks(): Flow<Resource<List<Stock>>>
    fun getBistStocks(): Flow<Resource<List<Stock>>>
    fun getBistGainers(): Flow<Resource<List<Stock>>>
    fun getBistLosers(): Flow<Resource<List<Stock>>>
    fun getUsGainers(): Flow<Resource<List<Stock>>>
    fun getUsLosers(): Flow<Resource<List<Stock>>>
    fun searchStocks(query: String): Flow<Resource<List<Stock>>>
    fun searchUsStocks(query: String): Flow<Resource<List<Stock>>>
    fun searchBistStocks(query: String): Flow<Resource<List<Stock>>>
}
