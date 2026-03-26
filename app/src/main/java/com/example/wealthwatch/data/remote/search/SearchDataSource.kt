package com.example.wealthwatch.data.remote.search

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.data.remote.model.SearchInitialResponse
import kotlinx.coroutines.flow.Flow

interface SearchDataSource {
    fun getSearchInitialData(): Flow<Resource<SearchInitialResponse>>
    fun searchAssets(query: String): Flow<Resource<List<com.example.wealthwatch.data.remote.model.AssetModel>>>
}