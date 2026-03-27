package com.example.wealthwatch.domain.use_case.search

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.domain.repository.remote.search.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchAssetsUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    operator fun invoke(query: String): Flow<Resource<List<MarketAsset>>> = repository.searchAssets(query)
}
