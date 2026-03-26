package com.example.wealthwatch.domain.model.currency

import com.example.wealthwatch.domain.model.asset.AssetType

data class Currency(
    val symbol: String,
    val name: String,
    val type: AssetType,
    val price: Double,
    val change: Double,
    val volume: Double,
    val marketCap: Double,
    val sector: String?,
    val iconUrl: String?
)
