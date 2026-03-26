package com.example.wealthwatch.data.remote.market

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.data.remote.BaseDataSource
import com.example.wealthwatch.data.remote.model.MarketOverviewResponse
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class RemoteMarketDataSource @Inject constructor(
    private val apiService: MarketApiService
) : BaseDataSource(), MarketDataSource {
    override fun getMarketOverview(): Flow<Resource<MarketOverviewResponse>> = getResult { apiService.getMarketOverview() }
}