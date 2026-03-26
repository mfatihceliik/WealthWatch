package com.example.wealthwatch.data.remote.stock

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.core.util.mapData
import com.example.wealthwatch.core.util.toSocketState
import com.example.wealthwatch.data.mapper.StockMapper
import com.example.wealthwatch.data.remote.BaseDataSource
import com.example.wealthwatch.data.remote.SocketService
import com.example.wealthwatch.domain.model.stock.Stock
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RemoteStockDataSource @Inject constructor(
    private val socketService: SocketService,
    private val apiService: StockApiService,
    private val stockMapper: StockMapper,
) : BaseDataSource(), StockDataSource  {

    override fun stockTickerUpdate(): Flow<SocketState<List<Stock>>> = socketService.stockTickerUpdate().toSocketState { stockMapper(it) }

    override fun getUsStocks(): Flow<Resource<List<Stock>>> =
        getResult { apiService.getUsStocks() }.map { resource ->
            resource.mapData { paginatedResponse ->
                stockMapper(paginatedResponse.data)
            }
        }

    override fun getBistStocks(): Flow<Resource<List<Stock>>> =
        getResult { apiService.getBistStocks() }.map { resource ->
            resource.mapData { paginatedResponse ->
                stockMapper(paginatedResponse.data)
            }
        }

    override fun getBistGainers(): Flow<Resource<List<Stock>>> =
        getResult { apiService.getBistGainers() }.map { resource ->
            resource.mapData { paginatedResponse ->
                stockMapper(paginatedResponse.data)
            }
        }

    override fun getBistLosers(): Flow<Resource<List<Stock>>> =
        getResult { apiService.getBistLosers() }.map { resource ->
            resource.mapData { paginatedResponse ->
                stockMapper(paginatedResponse.data)
            }
        }

    override fun getUsGainers(): Flow<Resource<List<Stock>>> =
        getResult { apiService.getUsGainers() }.map { resource ->
            resource.mapData { paginatedResponse ->
                stockMapper(paginatedResponse.data)
            }
        }

    override fun getUsLosers(): Flow<Resource<List<Stock>>> =
        getResult { apiService.getUsLosers() }.map { resource ->
            resource.mapData { paginatedResponse ->
                stockMapper(paginatedResponse.data)
            }
        }

    override fun searchStocks(query: String): Flow<Resource<List<Stock>>> =
        getResult { apiService.searchStocks(query = query) }.map { resource ->
            resource.mapData { paginatedResponse ->
                stockMapper(paginatedResponse.data)
            }
        }

    override fun searchUsStocks(query: String): Flow<Resource<List<Stock>>> =
        getResult { apiService.searchUsStocks(query = query) }.map { resource ->
            resource.mapData { paginatedResponse ->
                stockMapper(paginatedResponse.data)
            }
        }

    override fun searchBistStocks(query: String): Flow<Resource<List<Stock>>> =
        getResult { apiService.searchBistStocks(query = query) }.map { resource ->
            resource.mapData { paginatedResponse ->
                stockMapper(paginatedResponse.data)
            }
        }

}