package com.example.wealthwatch.domain.model.market

import com.example.wealthwatch.domain.model.asset.MarketAsset

data class Market(
    val pulse: List<MarketAsset>,
    val crypto: MarketCategory,
    val usStock: MarketCategory,
    val trStock: MarketCategory,
    val currency: MarketCategory,
    val commodity: MarketCategory
)

