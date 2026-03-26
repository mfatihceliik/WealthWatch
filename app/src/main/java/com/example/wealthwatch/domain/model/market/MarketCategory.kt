package com.example.wealthwatch.domain.model.market

import com.example.wealthwatch.domain.model.asset.MarketAsset

data class MarketCategory(
    val gainers: List<MarketAsset> = emptyList(),
    val losers: List<MarketAsset> = emptyList()
)
