package com.example.wealthwatch.data.mapper

import com.example.wealthwatch.core.mapper.BaseMapper
import com.example.wealthwatch.data.remote.model.CryptoModel
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.domain.model.asset.MarketAsset
import javax.inject.Inject

class CryptoMapper @Inject constructor() : BaseMapper<CryptoModel, MarketAsset>() {
    override fun map(input: CryptoModel): MarketAsset {

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
            icon = input.iconUrl ?: "",
            type = type,
            currentPrice = input.lastPrice.toDoubleOrNull() ?: 0.0,
            priceChangePercent = input.priceChangePercent.toDoubleOrNull() ?: 0.0,
            priceChange = input.priceChange.toDoubleOrNull() ?: 0.0,
            volume = input.quoteVolume.toDoubleOrNull() ?: 0.0, // quoteVolume used for sorting
        )
    }
}
