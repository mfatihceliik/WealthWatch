package com.example.wealthwatch.domain.model.search

import com.example.wealthwatch.domain.model.asset.MarketAsset

data class SearchInitialData(
    val topMovers: List<MarketAsset>,
    val suggestedAssets: List<MarketAsset>
)
