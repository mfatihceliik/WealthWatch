package com.example.wealthwatch.data.remote.currency

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.domain.model.currency.Currency
import com.example.wealthwatch.data.remote.model.CurrencyRateResponse
import kotlinx.coroutines.flow.Flow

interface CurrencyDataSource {
    fun currencyTickerUpdate(): Flow<SocketState<List<Currency>>>
    fun getCurrencies(): Flow<Resource<List<Currency>>>
    fun searchCurrencies(query: String): Flow<Resource<List<Currency>>>
    fun getCurrencyRates(): Flow<Resource<CurrencyRateResponse>>
    fun getCurrencyGainers(): Flow<Resource<List<Currency>>>
    fun getCurrencyLosers(): Flow<Resource<List<Currency>>>
}