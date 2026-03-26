package com.example.wealthwatch.data.mapper

import com.example.wealthwatch.core.mapper.BaseMapper
import com.example.wealthwatch.data.remote.model.CurrencyModel
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.domain.model.currency.Currency
import jakarta.inject.Inject

class CurrencyMapper @Inject constructor() : BaseMapper<CurrencyModel, Currency>() {
    override fun map(input: CurrencyModel): Currency {

        val type = when(input.type) {
            AssetType.CRYPTO.code -> AssetType.CRYPTO
            AssetType.US_STOCK.code -> AssetType.US_STOCK
            AssetType.TR_STOCK.code -> AssetType.TR_STOCK
            AssetType.CURRENCY.code -> AssetType.CURRENCY
            AssetType.COMMODITY.code -> AssetType.COMMODITY
            else -> AssetType.OTHER
        }

        return Currency(
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