package com.example.wealthwatch.data.mapper

import com.example.wealthwatch.core.mapper.BaseMapper
import com.example.wealthwatch.data.remote.model.CommoditiesModel
import com.example.wealthwatch.domain.model.commodities.Commodities
import com.example.wealthwatch.core.util.WealthWatchFormatter
import com.example.wealthwatch.domain.model.asset.AssetType
import jakarta.inject.Inject

class CommoditiesMapper @Inject constructor() : BaseMapper<CommoditiesModel, Commodities>() {
    override fun map(input: CommoditiesModel): Commodities {

        val type = when(input.type) {
            AssetType.CRYPTO.code -> AssetType.CRYPTO
            AssetType.US_STOCK.code -> AssetType.US_STOCK
            AssetType.TR_STOCK.code -> AssetType.TR_STOCK
            AssetType.CURRENCY.code -> AssetType.CURRENCY
            AssetType.COMMODITY.code -> AssetType.COMMODITY
            else -> AssetType.OTHER
        }

        return Commodities(
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