package com.example.wealthwatch.data.mapper

import com.example.wealthwatch.core.mapper.BaseMapper
import com.example.wealthwatch.data.local.entity.asset.AssetEntity
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.domain.model.asset.PortfolioAsset
import javax.inject.Inject

class AssetMapper @Inject constructor(
    private val assetModelMapper: AssetModelMapper
): BaseMapper<AssetEntity, PortfolioAsset>() {

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
                volume = 0.0,
            ),
            totalAmount = input.totalAmount,
            averageCost = input.averageCost
        )
    }
}

fun AssetEntity.toPortfolioAsset(marketAsset: MarketAsset): PortfolioAsset {
    return PortfolioAsset(
        marketAsset = marketAsset,
        totalAmount = this.totalAmount,
        averageCost = this.averageCost
    )
}

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
