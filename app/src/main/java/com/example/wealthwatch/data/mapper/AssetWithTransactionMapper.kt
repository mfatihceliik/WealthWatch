package com.example.wealthwatch.data.mapper

import com.example.wealthwatch.core.mapper.BaseMapper
import com.example.wealthwatch.data.local.entity.asset.AssetWithTransactions
import com.example.wealthwatch.domain.model.asset.MarketAsset
import javax.inject.Inject
import com.example.wealthwatch.domain.model.asset.AssetWithTransactions as DomainAssetWithTransactions

class AssetWithTransactionMapper @Inject constructor(
    private val transactionMapper: TransactionMapper
) : BaseMapper<AssetWithTransactions, DomainAssetWithTransactions>() {

    override fun map(input: AssetWithTransactions): DomainAssetWithTransactions {
        // Construct a partial MarketAsset from the cached DB entity
        val marketAsset = MarketAsset(
            symbol = input.asset.symbol,
            name = input.asset.name,
            icon = input.asset.icon,
            type = input.asset.type,
            currentPrice = input.asset.currentPrice,
            priceChange = 0.0,
            priceChangePercent = 0.0,
            volume = 0.0,
            lastUpdate = System.currentTimeMillis()
        )
        
        return DomainAssetWithTransactions(
            asset = input.asset.toPortfolioAsset(marketAsset),
            transactions = input.transactions.map { transactionMapper.map(it) }
        )
    }
}
