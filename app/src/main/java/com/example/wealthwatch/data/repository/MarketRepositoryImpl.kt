package com.example.wealthwatch.data.repository

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.data.remote.market.MarketDataSource
import com.example.wealthwatch.data.remote.model.MarketOverviewResponse
import com.example.wealthwatch.domain.repository.remote.market.MarketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
    private val remote: MarketDataSource
) : MarketRepository {
    override fun getMarketOverview(): Flow<Resource<MarketOverviewResponse>> = remote.getMarketOverview()
}