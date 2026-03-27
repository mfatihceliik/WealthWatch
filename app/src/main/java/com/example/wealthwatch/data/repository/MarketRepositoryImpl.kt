package com.example.wealthwatch.data.repository

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.mapData
import com.example.wealthwatch.data.mapper.MarketMapper
import com.example.wealthwatch.data.remote.market.MarketDataSource
import com.example.wealthwatch.domain.model.market.Market
import com.example.wealthwatch.domain.repository.remote.market.MarketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
    private val remote: MarketDataSource,
    private val marketMapper: MarketMapper
) : MarketRepository {
    override fun getMarketOverview(): Flow<Resource<Market>> = 
        remote.getMarketOverview().map { resource ->
            resource.mapData { response ->
                Market(
                    pulse = response.pulse.map { marketMapper.map(it) },
                    crypto = marketMapper.map(response.crypto),
                    usStock = marketMapper.map(response.usStock),
                    trStock = marketMapper.map(response.trStock),
                    currency = marketMapper.map(response.currency),
                    commodity = marketMapper.map(response.commodity)
                )
            }
        }
}