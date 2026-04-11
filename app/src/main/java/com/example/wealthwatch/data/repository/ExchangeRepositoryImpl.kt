package com.example.wealthwatch.data.repository

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.data.remote.currency.ExchangeDataSource
import com.example.wealthwatch.data.remote.model.ExchangeRateResponse
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.domain.repository.remote.currency.ExchangeRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class ExchangeRepositoryImpl @Inject constructor(
    private val remote: ExchangeDataSource,
) : ExchangeRepository {

    override fun tickerUpdate(): Flow<SocketState<List<MarketAsset>>> = remote.exchangeTickerUpdate()

    override fun getExchanges(): Flow<Resource<List<MarketAsset>>> = remote.getExchange()

    override fun searchExchanges(query: String): Flow<Resource<List<MarketAsset>>> = remote.searchExchanges(query = query)

    override fun getExchangeGainers(): Flow<Resource<List<MarketAsset>>> = remote.getExchangeGainers()

    override fun getExchangeLosers(): Flow<Resource<List<MarketAsset>>> = remote.getExchangeLosers()

    override suspend fun fetchAndSaveRates(): Flow<Resource<ExchangeRateResponse>> = remote.getExchangeRates()
}