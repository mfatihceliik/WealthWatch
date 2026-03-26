package com.example.wealthwatch.domain.use_case.search

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.data.mapper.SearchMapper
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.domain.repository.remote.search.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchAssetsUseCase @Inject constructor(
    private val repository: SearchRepository,
    private val searchMapper: SearchMapper
) {
    operator fun invoke(query: String): Flow<Resource<List<MarketAsset>>> {
        return repository.searchAssets(query).map { resource ->
            when (resource) {
                is Resource.Success -> {
                    val assets = withContext(Dispatchers.Default) { resource.data.map { searchMapper.mapModelToAsset(it) } }
                    Resource.Success(assets)
                }
                is Resource.Error -> Resource.Error(resource.message)
                is Resource.Loading -> Resource.Loading
            }
        }
    }
}
