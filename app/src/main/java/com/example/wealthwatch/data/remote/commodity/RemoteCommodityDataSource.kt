package com.example.wealthwatch.data.remote.commodity

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.mapData
import com.example.wealthwatch.data.mapper.CommoditiesMapper
import com.example.wealthwatch.data.remote.BaseDataSource
import com.example.wealthwatch.data.remote.SocketService
import com.example.wealthwatch.domain.model.asset.MarketAsset
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RemoteCommodityDataSource @Inject constructor(
    private val socketService: SocketService,
    private val apiService: CommodityApiService,
    private val commoditiesMapper: CommoditiesMapper
) : BaseDataSource(), CommodityDataSource {

    override fun getCommodities(): Flow<Resource<List<MarketAsset>>> =
        getResult { apiService.getCommodities() }.map { resource ->
            resource.mapData { paginatedResponse ->
                commoditiesMapper(paginatedResponse.data)
            }
        }

    override fun searchCommodities(query: String): Flow<Resource<List<MarketAsset>>> =
        getResult { apiService.searchCommodities(query = query) }.map { resource ->
            resource.mapData { paginatedResponse ->
                commoditiesMapper(paginatedResponse.data)
            }
        }

    override fun getCommoditiesGainers(): Flow<Resource<List<MarketAsset>>> =
        getResult { apiService.getCommoditiesGainers() }.map { resource ->
            resource.mapData { paginatedResponse ->
                commoditiesMapper(paginatedResponse.data)
            }
        }

    override fun getCommoditiesLosers(): Flow<Resource<List<MarketAsset>>> =
        getResult { apiService.getCommoditiesLosers() }.map { resource ->
            resource.mapData { paginatedResponse ->
                commoditiesMapper(paginatedResponse.data)
            }
        }
}