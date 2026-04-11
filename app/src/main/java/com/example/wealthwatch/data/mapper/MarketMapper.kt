package com.example.wealthwatch.data.mapper

import com.example.wealthwatch.core.mapper.BaseMapper
import com.example.wealthwatch.data.remote.model.MarketCategoryModel
import com.example.wealthwatch.data.remote.model.MarketModel
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.domain.model.market.MarketCategory
import javax.inject.Inject

class MarketMapper @Inject constructor() : BaseMapper<MarketModel, MarketAsset>() {

    override fun map(input: MarketModel): MarketAsset {

        val type = when {
            input.assetType != null -> AssetType.fromCode(input.assetType.name)
            input.type.isNotEmpty() -> AssetType.fromCode(input.type)
            input.assetTypeId != null -> when (input.assetTypeId) {
                1 -> AssetType.US_STOCK
                2 -> AssetType.BIST
                3 -> AssetType.CRYPTO
                4 -> AssetType.COMMODITY
                5 -> AssetType.EXCHANGE
                else -> AssetType.OTHER
            }
            else -> AssetType.OTHER
        }


        return MarketAsset(
            symbol = input.symbol,
            name = input.name,
            icon = input.iconUrl,
            type = type,
            currentPrice = input.price ?: input.lastPrice ?: 0.0,
            priceChange = input.priceChange ?: 0.0,
            priceChangePercent = input.priceChangePercent ?: input.changePercent ?: 0.0,
            volume = input.volume ?: 0.0
        )
    }
    fun map(input: MarketCategoryModel): MarketCategory {
        return MarketCategory(
            gainers = input.gainers.map { map(it) },
            losers = input.losers.map { map(it) }
        )
    }
}