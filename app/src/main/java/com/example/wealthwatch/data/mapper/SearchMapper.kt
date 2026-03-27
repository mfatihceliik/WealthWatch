package com.example.wealthwatch.data.mapper

import com.example.wealthwatch.core.mapper.BaseMapper
import com.example.wealthwatch.data.remote.model.SearchInitialResponse
import com.example.wealthwatch.domain.model.search.SearchInitialData
import javax.inject.Inject

class SearchMapper @Inject constructor(
    private val assetModelMapper: AssetModelMapper
): BaseMapper<SearchInitialResponse, SearchInitialData>() {

    override fun map(input: SearchInitialResponse): SearchInitialData {
        return SearchInitialData(
            topMovers = input.topMovers?.map { assetModelMapper.map(it) } ?: emptyList(),
            suggestedAssets = input.suggestedAssets?.map { assetModelMapper.map(it) } ?: emptyList()
        )
    }

    fun mapModelToAsset(dto: com.example.wealthwatch.data.remote.model.AssetModel): com.example.wealthwatch.domain.model.asset.MarketAsset {
        return assetModelMapper.map(dto)
    }
}
