package com.example.wealthwatch.domain.model.asset

import com.example.wealthwatch.domain.model.PriceTrend

data class MarketAsset(
    val symbol: String,
    val name: String,
    val icon: String,
    val type: AssetType,
    val currentPrice: Double,
    val priceChange: Double,
    val priceChangePercent: Double,
    val volume: Double,
    val lastUpdate: Long = System.currentTimeMillis()
)
