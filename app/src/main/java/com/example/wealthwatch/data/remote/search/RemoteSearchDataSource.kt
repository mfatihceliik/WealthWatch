package com.example.wealthwatch.data.remote.search

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.data.remote.BaseDataSource
import com.example.wealthwatch.data.remote.model.SearchInitialResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteSearchDataSource @Inject constructor(
    private val api: SearchApiService
) : BaseDataSource(), SearchDataSource {
    override fun getSearchInitialData(): Flow<Resource<SearchInitialResponse>> = getResult { api.getSearchInitialData() }

    override fun searchAssets(query: String): Flow<Resource<List<com.example.wealthwatch.data.remote.model.AssetModel>>> {
        return getResult { api.searchAssets(query) }
    }
}
