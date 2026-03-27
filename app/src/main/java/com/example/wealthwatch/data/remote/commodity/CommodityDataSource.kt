package com.example.wealthwatch.data.remote.commodity

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.domain.model.asset.MarketAsset
import kotlinx.coroutines.flow.Flow

interface CommodityDataSource {
    fun getCommodities(): Flow<Resource<List<MarketAsset>>>
    fun searchCommodities(query: String): Flow<Resource<List<MarketAsset>>>
    fun getCommoditiesGainers(): Flow<Resource<List<MarketAsset>>>
    fun getCommoditiesLosers(): Flow<Resource<List<MarketAsset>>>
}