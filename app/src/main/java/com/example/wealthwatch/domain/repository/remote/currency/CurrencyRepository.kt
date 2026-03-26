package com.example.wealthwatch.domain.repository.remote.currency

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.data.remote.model.CurrencyRateResponse
import com.example.wealthwatch.domain.model.currency.Currency
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    fun tickerUpdate(): Flow<SocketState<List<Currency>>>
    fun getCurrencies(): Flow<Resource<List<Currency>>>
    fun searchCurrencies(query: String): Flow<Resource<List<Currency>>>
    fun getCurrencyGainers(): Flow<Resource<List<Currency>>>
    fun getCurrencyLosers(): Flow<Resource<List<Currency>>>
    suspend fun fetchAndSaveRates(): Flow<Resource<CurrencyRateResponse>>
}