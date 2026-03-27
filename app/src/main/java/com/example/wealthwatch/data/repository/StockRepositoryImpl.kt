package com.example.wealthwatch.data.repository

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.data.remote.stock.StockDataSource
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.domain.repository.remote.stock.StockRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class StockRepositoryImpl @Inject constructor(
    private val remote: StockDataSource
) : StockRepository {
    override fun stockTickerUpdate(): Flow<SocketState<List<MarketAsset>>> = remote.stockTickerUpdate()

    override fun getUsStocks(): Flow<Resource<List<MarketAsset>>> = remote.getUsStocks()

    override fun getBistStocks(): Flow<Resource<List<MarketAsset>>> = remote.getBistStocks()

    override fun getBistGainers(): Flow<Resource<List<MarketAsset>>> = remote.getBistGainers()

    override fun getBistLosers(): Flow<Resource<List<MarketAsset>>> = remote.getBistLosers()

    override fun getUsGainers(): Flow<Resource<List<MarketAsset>>> = remote.getUsGainers()

    override fun getUsLosers(): Flow<Resource<List<MarketAsset>>> = remote.getUsLosers()

    override fun searchStocks(query: String): Flow<Resource<List<MarketAsset>>> = remote.searchStocks(query = query)

    override fun searchUsStocks(query: String): Flow<Resource<List<MarketAsset>>> = remote.searchUsStocks(query = query)

    override fun searchBistStocks(query: String): Flow<Resource<List<MarketAsset>>> = remote.searchBistStocks(query = query)
}
