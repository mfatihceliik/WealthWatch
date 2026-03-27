package com.example.wealthwatch.data.repository

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.data.remote.currency.CurrencyDataSource
import com.example.wealthwatch.data.remote.model.CurrencyRateResponse
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.domain.repository.remote.currency.CurrencyRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class CurrencyRepositoryImpl @Inject constructor(
    private val remote: CurrencyDataSource,
) : CurrencyRepository {

    override fun tickerUpdate(): Flow<SocketState<List<MarketAsset>>> = remote.currencyTickerUpdate()

    override fun getCurrencies(): Flow<Resource<List<MarketAsset>>> = remote.getCurrencies()

    override fun searchCurrencies(query: String): Flow<Resource<List<MarketAsset>>> = remote.searchCurrencies(query = query)

    override fun getCurrencyGainers(): Flow<Resource<List<MarketAsset>>> = remote.getCurrencyGainers()

    override fun getCurrencyLosers(): Flow<Resource<List<MarketAsset>>> = remote.getCurrencyLosers()

    override suspend fun fetchAndSaveRates(): Flow<Resource<CurrencyRateResponse>> = remote.getCurrencyRates()
}