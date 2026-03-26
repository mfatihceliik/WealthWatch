package com.example.wealthwatch.domain.model.market

import com.example.wealthwatch.domain.model.asset.AssetWithTransactions
import com.example.wealthwatch.domain.model.settings.AppCurrency

data class MarketDashboard(
    val market: Market,
    val assets: List<AssetWithTransactions>,
    val rates: Map<String, Double>,
    val currency: AppCurrency,
    val totalBalance: Double
)
