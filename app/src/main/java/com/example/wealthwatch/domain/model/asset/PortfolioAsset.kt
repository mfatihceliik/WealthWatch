package com.example.wealthwatch.domain.model.asset

// Contains both the market data related to the asset
// AND the user's personal holding data
data class PortfolioAsset(
    val marketAsset: MarketAsset,
    val totalAmount: Double,
    val averageCost: Double
) {
    val totalValue: Double
        get() = marketAsset.currentPrice * totalAmount
        
    val totalProfitLoss: Double
        get() = (marketAsset.currentPrice - averageCost) * totalAmount
        
    val profitLossPercentage: Double
        get() = if (averageCost > 0.0) ((marketAsset.currentPrice - averageCost) / averageCost) * 100 else 0.0
}
