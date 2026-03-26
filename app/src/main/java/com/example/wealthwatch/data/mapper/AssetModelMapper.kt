package com.example.wealthwatch.data.mapper

import com.example.wealthwatch.core.mapper.BaseMapper
import com.example.wealthwatch.data.remote.model.AssetModel
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.domain.model.asset.AssetType
import jakarta.inject.Inject

class AssetModelMapper @Inject constructor() {

    fun map(input: AssetModel): MarketAsset = MarketAsset(
        symbol = input.symbol ?: "",
        name = input.name ?: "",
        type = AssetType.fromCode(input.assetType?.name ?: ""),
        icon = input.iconUrl ?: "",
        currentPrice = input.price ?: 0.0,
        priceChange = input.priceChange ?: 0.0,
        priceChangePercent = input.changePercent ?: 0.0,
        volume = input.volume ?: 0.0,
    )
}