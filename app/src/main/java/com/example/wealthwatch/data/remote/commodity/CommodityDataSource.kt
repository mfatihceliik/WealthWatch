package com.example.wealthwatch.data.remote.commodity

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.domain.model.commodities.Commodities
import kotlinx.coroutines.flow.Flow

interface CommodityDataSource {
    fun getCommodities(): Flow<Resource<List<Commodities>>>
    fun searchCommodities(query: String): Flow<Resource<List<Commodities>>>
    fun getCommoditiesGainers(): Flow<Resource<List<Commodities>>>
    fun getCommoditiesLosers(): Flow<Resource<List<Commodities>>>
}