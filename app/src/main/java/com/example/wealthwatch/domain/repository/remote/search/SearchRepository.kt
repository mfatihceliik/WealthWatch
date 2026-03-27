package com.example.wealthwatch.domain.repository.remote.search

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.domain.model.search.SearchInitialData
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getSearchInitialData(): Flow<Resource<SearchInitialData>>
    fun searchAssets(query: String): Flow<Resource<List<MarketAsset>>>
}