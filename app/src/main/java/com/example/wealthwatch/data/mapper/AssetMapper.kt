package com.example.wealthwatch.data.mapper

import com.example.wealthwatch.data.local.entity.asset.AssetEntity
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.domain.model.asset.PortfolioAsset

// Create PortfolioAsset from Entity and MarketAsset
fun AssetEntity.toPortfolioAsset(marketAsset: MarketAsset): PortfolioAsset {
    return PortfolioAsset(
        marketAsset = marketAsset,
        totalAmount = this.totalAmount,
        averageCost = this.averageCost
    )
}

// Extract Entity from PortfolioAsset
fun PortfolioAsset.toEntity(): AssetEntity {
    return AssetEntity(
        symbol = this.marketAsset.symbol,
        name = this.marketAsset.name,
        type = this.marketAsset.type,
        icon = this.marketAsset.icon,
        totalAmount = this.totalAmount,
        averageCost = this.averageCost,
        currentPrice = this.marketAsset.currentPrice
    )
}

