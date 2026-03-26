package com.example.wealthwatch.data.remote.currency

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.core.util.mapData
import com.example.wealthwatch.core.util.toSocketState
import com.example.wealthwatch.data.mapper.CurrencyMapper
import com.example.wealthwatch.data.remote.BaseDataSource
import com.example.wealthwatch.data.remote.SocketService
import com.example.wealthwatch.domain.model.currency.Currency
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.wealthwatch.data.remote.model.CurrencyRateResponse

class RemoteCurrencyDataSource @Inject constructor(
    private val socketService: SocketService,
    private val apiService: CurrencyApiService,
    private val currencyMapper: CurrencyMapper
) : BaseDataSource(), CurrencyDataSource {

    override fun currencyTickerUpdate(): Flow<SocketState<List<Currency>>> = socketService.currencyTickerUpdate().toSocketState { currencyMapper(it) }

    override fun getCurrencies(): Flow<Resource<List<Currency>>> =
        getResult { apiService.getCurrencies() }.map { resource ->
            resource.mapData { paginatedResponse ->
                currencyMapper(paginatedResponse.data)
            }
        }

    override fun searchCurrencies(query: String): Flow<Resource<List<Currency>>> =
        getResult { apiService.searchCurrencies(query = query) }.map { resource ->
            resource.mapData { paginatedResponse ->
                currencyMapper(paginatedResponse.data)
            }
        }

    override fun getCurrencyRates(): Flow<Resource<CurrencyRateResponse>> =
        getResult { apiService.getCurrencyRates() }

    override fun getCurrencyGainers(): Flow<Resource<List<Currency>>> =
        getResult { apiService.getCurrencyGainers() }.map { resource ->
            resource.mapData { paginatedResponse ->
                currencyMapper(paginatedResponse.data)
            }
        }

    override fun getCurrencyLosers(): Flow<Resource<List<Currency>>> =
        getResult { apiService.getCurrencyLosers() }.map { resource ->
            resource.mapData { paginatedResponse ->
                currencyMapper(paginatedResponse.data)
            }
        }

}