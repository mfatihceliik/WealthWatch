package com.example.wealthwatch.data.repository

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.data.remote.model.AssetModel
import com.example.wealthwatch.data.remote.model.SearchInitialResponse
import com.example.wealthwatch.data.remote.search.SearchDataSource
import com.example.wealthwatch.domain.repository.remote.search.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: SearchDataSource
) : SearchRepository {
    override fun getSearchInitialData(): Flow<Resource<SearchInitialResponse>> = remoteDataSource.getSearchInitialData()

    override fun searchAssets(query: String): Flow<Resource<List<AssetModel>>> = remoteDataSource.searchAssets(query)
}