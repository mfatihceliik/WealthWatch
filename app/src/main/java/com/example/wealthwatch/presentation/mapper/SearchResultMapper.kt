package com.example.wealthwatch.presentation.mapper

import com.example.wealthwatch.domain.model.PriceTrend
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.domain.model.commodities.Commodities
import com.example.wealthwatch.domain.model.crypto.Crypto
import com.example.wealthwatch.domain.model.currency.Currency
import com.example.wealthwatch.domain.model.stock.Stock
import javax.inject.Inject

class SearchResultMapper @Inject constructor() {

    fun mapCryptoToAsset(crypto: Crypto): MarketAsset {
        val p = crypto.price
        val cp = crypto.priceChangePercent.toDoubleOrNull() ?: 0.0
        return MarketAsset(
            symbol = crypto.symbol,
            name = crypto.name,
            icon = crypto.iconUrl ?: "",
            type = crypto.type,
            currentPrice = p,
            priceChange = 0.0,
            priceChangePercent = cp,
            volume = 0.0
        )
    }

    fun mapStockToAsset(stock: Stock): MarketAsset {
        val trend = if (stock.change >= 0) PriceTrend.UP else PriceTrend.DOWN
        return MarketAsset(
            symbol = stock.symbol,
            name = stock.name,
            icon = stock.iconUrl ?: "",
            type = stock.type,
            currentPrice = stock.price,
            priceChange = stock.change,
            priceChangePercent = 0.0,
            volume = 0.0
        )
    }

    fun mapCurrencyToAsset(currency: Currency): MarketAsset {
        val trend = if (currency.change >= 0) PriceTrend.UP else PriceTrend.DOWN
        return MarketAsset(
            symbol = currency.symbol,
            name = currency.name,
            icon = currency.iconUrl ?: "",
            type = currency.type,
            currentPrice = currency.price,
            priceChange = currency.change,
            priceChangePercent = 0.0,
            volume = 0.0
        )
    }

    fun mapCommodityToAsset(commodity: Commodities): MarketAsset {
        val trend = if (commodity.change >= 0) PriceTrend.UP else PriceTrend.DOWN
        return MarketAsset(
            symbol = commodity.symbol,
            name = commodity.name,
            icon = commodity.iconUrl ?: "",
            type = commodity.type,
            currentPrice = commodity.price,
            priceChange = commodity.change,
            priceChangePercent = 0.0,
            volume = 0.0
        )
    }
}
