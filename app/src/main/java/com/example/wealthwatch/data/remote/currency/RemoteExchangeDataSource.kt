package com.example.wealthwatch.data.remote.currency

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.core.util.mapData
import com.example.wealthwatch.core.util.toSocketState
import com.example.wealthwatch.data.mapper.ExchangeMapper
import com.example.wealthwatch.data.remote.BaseDataSource
import com.example.wealthwatch.data.remote.SocketService
import com.example.wealthwatch.domain.model.asset.MarketAsset
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.wealthwatch.data.remote.model.ExchangeRateResponse

class RemoteExchangeDataSource @Inject constructor(
    private val socketService: SocketService,
    private val apiService: ExchangeApiService,
    private val exchangeMapper: ExchangeMapper
) : BaseDataSource(), ExchangeDataSource {

    override fun exchangeTickerUpdate(): Flow<SocketState<List<MarketAsset>>> = socketService.currencyTickerUpdate().toSocketState { exchangeMapper(it) }

    override fun getExchange(): Flow<Resource<List<MarketAsset>>> =
        getResult { apiService.getExchanges() }.map { resource ->
            resource.mapData { paginatedResponse ->
                exchangeMapper(paginatedResponse.data)
            }
        }

    override fun searchExchanges(query: String): Flow<Resource<List<MarketAsset>>> =
        getResult { apiService.searchExchange(query = query) }.map { resource ->
            resource.mapData { paginatedResponse ->
                exchangeMapper(paginatedResponse.data)
            }
        }

    override fun getExchangeRates(): Flow<Resource<ExchangeRateResponse>> =
        getResult { apiService.getExchangeRates() }

    override fun getExchangeGainers(): Flow<Resource<List<MarketAsset>>> =
        getResult { apiService.getExchangeGainers() }.map { resource ->
            resource.mapData { paginatedResponse ->
                exchangeMapper(paginatedResponse.data)
            }
        }

    override fun getExchangeLosers(): Flow<Resource<List<MarketAsset>>> =
        getResult { apiService.getExchangeLosers() }.map { resource ->
            resource.mapData { paginatedResponse ->
                exchangeMapper(paginatedResponse.data)
            }
        }
}