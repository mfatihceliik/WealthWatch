package com.example.wealthwatch.domain.use_case.search

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.domain.repository.remote.search.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.wealthwatch.data.mapper.SearchMapper
import javax.inject.Inject

class GetSearchInitialDataUseCase @Inject constructor(
    private val repository: SearchRepository,
    private val searchMapper: SearchMapper
) {
    operator fun invoke(): Flow<Resource<SearchInitialData>> {
        return repository.getSearchInitialData().map { resource ->
            when (resource) {
                is Resource.Success -> {
                    val data = withContext(Dispatchers.Default) { searchMapper.map(resource.data) }
                    Resource.Success(data)
                }
                is Resource.Error -> Resource.Error(resource.message)
                is Resource.Loading -> Resource.Loading
            }
        }
    }
}

data class SearchInitialData(
    val topMovers: List<MarketAsset>,
    val suggestedAssets: List<MarketAsset>
)
