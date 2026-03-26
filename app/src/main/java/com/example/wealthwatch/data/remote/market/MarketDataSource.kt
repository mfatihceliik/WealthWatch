package com.example.wealthwatch.data.remote.market

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.data.remote.model.MarketOverviewResponse
import kotlinx.coroutines.flow.Flow

interface MarketDataSource {
    fun getMarketOverview(): Flow<Resource<MarketOverviewResponse>>
}