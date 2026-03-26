package com.example.wealthwatch.data.mapper

import com.example.wealthwatch.core.mapper.BaseMapper
import com.example.wealthwatch.data.remote.model.AssetModel
import com.example.wealthwatch.data.remote.model.SearchInitialResponse
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.domain.use_case.search.SearchInitialData
import javax.inject.Inject

class SearchMapper @Inject constructor() {

    fun map(input: SearchInitialResponse): SearchInitialData {
        return SearchInitialData(
            topMovers = input.topMovers?.map { mapModelToAsset(it) } ?: emptyList(),
            suggestedAssets = input.suggestedAssets?.map { mapModelToAsset(it) } ?: emptyList()
        )
    }

    fun mapModelToAsset(dto: AssetModel): MarketAsset {
        val changePercent = dto.changePercent ?: 0.0
        
        return MarketAsset(
            symbol = dto.symbol ?: "",
            name = dto.name ?: "",
            icon = dto.iconUrl ?: "",
            type = AssetType.fromCode(dto.assetType?.name ?: ""),
            currentPrice = dto.price ?: 0.0,
            priceChange = dto.priceChange ?: 0.0,
            priceChangePercent = changePercent,
            volume = dto.volume ?: 0.0
        )
    }
}
