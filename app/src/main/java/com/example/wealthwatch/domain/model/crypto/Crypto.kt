package com.example.wealthwatch.domain.model.crypto

import com.example.wealthwatch.domain.model.asset.AssetType

data class Crypto(
    val symbol: String,
    val name: String,
    val type: AssetType,
    val price: Double,
    val priceChangePercent: String,
    val priceChange: String,
    val volume: String,
    val quoteVolume: String,
    val iconUrl: String?
)
