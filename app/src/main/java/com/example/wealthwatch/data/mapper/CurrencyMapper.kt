package com.example.wealthwatch.data.mapper

import com.example.wealthwatch.core.mapper.BaseMapper
import com.example.wealthwatch.data.remote.model.CurrencyModel
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.domain.model.asset.MarketAsset
import jakarta.inject.Inject

class CurrencyMapper @Inject constructor() : BaseMapper<CurrencyModel, MarketAsset>() {
    override fun map(input: CurrencyModel): MarketAsset {

        val type = when (input.type) {
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
            priceChange = input.change,
            priceChangePercent = 0.0,
            volume = input.volume,
        )
    }
}