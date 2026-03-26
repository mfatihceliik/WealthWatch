package com.example.wealthwatch.domain.repository.remote.search

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.data.remote.model.AssetModel
import com.example.wealthwatch.data.remote.model.SearchInitialResponse
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getSearchInitialData(): Flow<Resource<SearchInitialResponse>>
    fun searchAssets(query: String): Flow<Resource<List<AssetModel>>>
}