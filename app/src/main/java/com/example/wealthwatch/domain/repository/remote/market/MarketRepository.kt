package com.example.wealthwatch.domain.repository.remote.market

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.data.remote.model.MarketOverviewResponse
import com.example.wealthwatch.domain.model.market.Market
import kotlinx.coroutines.flow.Flow

interface MarketRepository {
    fun getMarketOverview(): Flow<Resource<MarketOverviewResponse>>
}
