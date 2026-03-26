package com.example.wealthwatch.data.mapper

import com.example.wealthwatch.core.mapper.BaseMapper
import com.example.wealthwatch.data.remote.model.StockModel
import com.example.wealthwatch.domain.model.stock.Stock
import com.example.wealthwatch.core.util.WealthWatchFormatter
import com.example.wealthwatch.domain.model.asset.AssetType
import javax.inject.Inject

class StockMapper @Inject constructor() : BaseMapper<StockModel, Stock>() {
    override fun map(input: StockModel): Stock {

        /*val typeString = input.type.trim().lowercase()
        val type = when {
            typeString.contains("crypto") || typeString.contains("coin") -> AssetType.CRYPTO
            typeString.contains("bist") || typeString in listOf("tr", "tr_stock", "borsa", "istanbul") -> AssetType.TR_STOCK
            typeString.contains("us_stock") || typeString in listOf("us", "nasdaq", "nyse", "stock") -> AssetType.US_STOCK
            typeString.contains("currency") || typeString.contains("forex") -> AssetType.CURRENCY
            typeString.contains("commodity") || typeString in listOf("metal", "gold", "silver") -> AssetType.COMMODITY
            else -> AssetType.fromCode(input.type.trim())
        }*/

        val type = when(input.type) {
            AssetType.CRYPTO.code -> AssetType.CRYPTO
            AssetType.US_STOCK.code -> AssetType.US_STOCK
            AssetType.TR_STOCK.code -> AssetType.TR_STOCK
            AssetType.CURRENCY.code -> AssetType.CURRENCY
            AssetType.COMMODITY.code -> AssetType.COMMODITY
            else -> AssetType.OTHER
        }

        return Stock(
            symbol = input.symbol,
            name = input.name,
            type = type,
            price = input.price,
            change = input.change,
            volume = input.volume,
            marketCap = input.marketCap,
            sector = input.sector,
            iconUrl = input.iconUrl
        )
    }
}