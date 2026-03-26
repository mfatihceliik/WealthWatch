package com.example.wealthwatch.domain.model.asset

data class AssetWithTransactions(
    val asset: PortfolioAsset,
    val transactions: List<Transaction>
)
