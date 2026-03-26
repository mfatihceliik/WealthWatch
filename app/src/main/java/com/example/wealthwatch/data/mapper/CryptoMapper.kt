package com.example.wealthwatch.data.mapper

import com.example.wealthwatch.core.mapper.BaseMapper
import com.example.wealthwatch.data.remote.model.CryptoModel
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.domain.model.crypto.Crypto
import javax.inject.Inject

class CryptoMapper @Inject constructor() : BaseMapper<CryptoModel, Crypto>() {
    override fun map(input: CryptoModel): Crypto {

        val type = when(input.type) {
            AssetType.CRYPTO.code -> AssetType.CRYPTO
            AssetType.US_STOCK.code -> AssetType.US_STOCK
            AssetType.TR_STOCK.code -> AssetType.TR_STOCK
            AssetType.CURRENCY.code -> AssetType.CURRENCY
            AssetType.COMMODITY.code -> AssetType.COMMODITY
            else -> AssetType.OTHER
        }

        return Crypto(
            symbol = input.symbol,
            name = input.name,
            type = type,
            price = input.lastPrice.toDoubleOrNull() ?: 0.0,
            priceChangePercent = input.priceChangePercent,
            priceChange = input.priceChange,
            volume = input.volume,
            quoteVolume = input.quoteVolume,
            iconUrl = input.iconUrl
        )
    }
}
