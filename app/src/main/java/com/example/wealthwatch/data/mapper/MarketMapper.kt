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

        val type = when(input.type) {
            AssetType.CRYPTO.code -> AssetType.CRYPTO
            AssetType.US_STOCK.code -> AssetType.US_STOCK
            AssetType.TR_STOCK.code -> AssetType.TR_STOCK
            AssetType.CURRENCY.code -> AssetType.CURRENCY
            AssetType.COMMODITY.code -> AssetType.COMMODITY
            else -> AssetType.OTHER
        }

        return MarketAsset(
            symbol = input.symbol,
            name = input.name,
            icon = input.iconUrl,
            type = type,
            currentPrice = input.price,
            priceChange = input.priceChange,
            priceChangePercent = input.priceChangePercent,
            volume = input.volume,
        )
    }
    fun map(input: MarketCategoryModel): MarketCategory {
        return MarketCategory(
            gainers = input.gainers.map { map(it) },
            losers = input.losers.map { map(it) }
        )
    }
}