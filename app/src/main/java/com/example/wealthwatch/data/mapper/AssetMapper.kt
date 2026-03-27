package com.example.wealthwatch.data.mapper

import com.example.wealthwatch.core.mapper.BaseMapper
import com.example.wealthwatch.data.local.entity.asset.AssetEntity
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.domain.model.asset.PortfolioAsset
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AssetMapper @Inject constructor() : BaseMapper<AssetEntity, PortfolioAsset>() {

    override fun map(input: AssetEntity): PortfolioAsset {
        return PortfolioAsset(
            marketAsset = MarketAsset(
                symbol = input.symbol,
                name = input.name,
                icon = input.icon,
                type = input.type,
                currentPrice = input.currentPrice,
                priceChange = 0.0,
                priceChangePercent = 0.0,
                volume = 0.0
            ),
            totalAmount = input.totalAmount,
            averageCost = input.averageCost
        )
    }

    fun map(entity: AssetEntity, marketAsset: MarketAsset): PortfolioAsset {
        return PortfolioAsset(
            marketAsset = marketAsset,
            totalAmount = entity.totalAmount,
            averageCost = entity.averageCost
        )
    }

    fun mapToEntity(portfolioAsset: PortfolioAsset): AssetEntity {
        return AssetEntity(
            symbol = portfolioAsset.marketAsset.symbol,
            name = portfolioAsset.marketAsset.name,
            type = portfolioAsset.marketAsset.type,
            icon = portfolioAsset.marketAsset.icon,
            totalAmount = portfolioAsset.totalAmount,
            averageCost = portfolioAsset.averageCost,
            currentPrice = portfolioAsset.marketAsset.currentPrice
        )
    }
}
