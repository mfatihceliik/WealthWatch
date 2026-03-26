package com.example.wealthwatch.data.repository

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.data.remote.stock.StockDataSource
import com.example.wealthwatch.domain.model.stock.Stock
import com.example.wealthwatch.domain.repository.remote.stock.StockRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class StockRepositoryImpl @Inject constructor(
    private val remote: StockDataSource
) : StockRepository {
    override fun stockTickerUpdate(): Flow<SocketState<List<Stock>>> = remote.stockTickerUpdate()

    override fun getUsStocks(): Flow<Resource<List<Stock>>> = remote.getUsStocks()

    override fun getBistStocks(): Flow<Resource<List<Stock>>> = remote.getBistStocks()

    override fun getBistGainers(): Flow<Resource<List<Stock>>> = remote.getBistGainers()

    override fun getBistLosers(): Flow<Resource<List<Stock>>> = remote.getBistLosers()

    override fun getUsGainers(): Flow<Resource<List<Stock>>> = remote.getUsGainers()

    override fun getUsLosers(): Flow<Resource<List<Stock>>> = remote.getUsLosers()

    override fun searchStocks(query: String): Flow<Resource<List<Stock>>> = remote.searchStocks(query = query)

    override fun searchUsStocks(query: String): Flow<Resource<List<Stock>>> = remote.searchUsStocks(query = query)

    override fun searchBistStocks(query: String): Flow<Resource<List<Stock>>> = remote.searchBistStocks(query = query)
}
