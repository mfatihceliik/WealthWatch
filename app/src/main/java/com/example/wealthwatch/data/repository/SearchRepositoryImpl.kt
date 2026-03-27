package com.example.wealthwatch.data.repository

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.mapData
import com.example.wealthwatch.data.mapper.SearchMapper
import com.example.wealthwatch.data.remote.search.SearchDataSource
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.domain.model.search.SearchInitialData
import com.example.wealthwatch.domain.repository.remote.search.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: SearchDataSource,
    private val searchMapper: SearchMapper
) : SearchRepository {
    override fun getSearchInitialData(): Flow<Resource<SearchInitialData>> = 
        remoteDataSource.getSearchInitialData().map { resource ->
            resource.mapData { response ->
                searchMapper.map(response)
            }
        }

    override fun searchAssets(query: String): Flow<Resource<List<MarketAsset>>> = 
        remoteDataSource.searchAssets(query).map { resource ->
            resource.mapData { list ->
                list.map { searchMapper.mapModelToAsset(it) }
            }
        }
}