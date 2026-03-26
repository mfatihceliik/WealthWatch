package com.example.wealthwatch.data.repository

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.data.remote.commodity.CommodityDataSource
import com.example.wealthwatch.di.ApplicationScope
import com.example.wealthwatch.domain.model.commodities.Commodities
import com.example.wealthwatch.domain.repository.remote.commodity.CommodityRepository
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class CommodityRepositoryImpl @Inject constructor(
    private val remote: CommodityDataSource,
    @param:ApplicationScope private val applicationScope: CoroutineScope,
) : CommodityRepository {

    override fun getCommodities(): Flow<Resource<List<Commodities>>> = remote.getCommodities()

    override fun searchCommodities(query: String): Flow<Resource<List<Commodities>>> = remote.searchCommodities(query = query)

    override fun getCommoditiesGainers(): Flow<Resource<List<Commodities>>> = remote.getCommoditiesGainers()

    override fun getCommoditiesLosers(): Flow<Resource<List<Commodities>>> = remote.getCommoditiesLosers()

}