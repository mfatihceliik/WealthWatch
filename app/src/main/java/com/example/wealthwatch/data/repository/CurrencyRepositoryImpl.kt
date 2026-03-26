package com.example.wealthwatch.data.repository

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.data.remote.currency.CurrencyDataSource
import com.example.wealthwatch.data.remote.model.CurrencyRateResponse
import com.example.wealthwatch.domain.model.currency.Currency
import com.example.wealthwatch.domain.repository.remote.currency.CurrencyRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

import com.example.wealthwatch.domain.repository.local.settings.SettingsRepository

class CurrencyRepositoryImpl @Inject constructor(
    private val remote: CurrencyDataSource,
): CurrencyRepository {

    override fun tickerUpdate(): Flow<SocketState<List<Currency>>> = remote.currencyTickerUpdate()

    override fun getCurrencies(): Flow<Resource<List<Currency>>> = remote.getCurrencies()

    override fun searchCurrencies(query: String): Flow<Resource<List<Currency>>> = remote.searchCurrencies(query = query)

    override fun getCurrencyGainers(): Flow<Resource<List<Currency>>> = remote.getCurrencyGainers()

    override fun getCurrencyLosers(): Flow<Resource<List<Currency>>> = remote.getCurrencyLosers()

    override suspend fun fetchAndSaveRates(): Flow<Resource<CurrencyRateResponse>> = remote.getCurrencyRates()
}