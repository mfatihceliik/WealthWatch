package com.example.wealthwatch.domain.use_case.transaction

import com.example.wealthwatch.domain.model.asset.PortfolioAsset
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.domain.model.asset.Transaction
import com.example.wealthwatch.domain.repository.local.asset.AssetRepository
import com.example.wealthwatch.domain.repository.local.transaction.TransactionRepository
import com.example.wealthwatch.domain.use_case.asset.RecalculateAssetUseCase
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val assetRepository: AssetRepository,
    private val transactionRepository: TransactionRepository,
    private val recalculateAssetUseCase: RecalculateAssetUseCase
) {
    data class Params(
        val symbol: String,
        val name: String,
        val type: AssetType,
        val amount: Double,
        val price: Double,
        val isBuy: Boolean,
        val note: String = "",
        val exchangeRate: Double = 1.0
    )

    suspend operator fun invoke(parameters: Params) {
        val (symbol, name, type, amount, price, isBuy, note, exchangeRate) = parameters

        // 1. Ensure Asset Exists
        var asset = assetRepository.getAsset(symbol)
        if (asset == null) {
            val marketAsset = MarketAsset(
                symbol = symbol,
                name = name,
                type = type,
                icon = "",
                currentPrice = price,
                priceChange = 0.0,
                priceChangePercent = 0.0,
                volume = 0.0
            )
            asset = PortfolioAsset(
                marketAsset = marketAsset,
                totalAmount = 0.0,
                averageCost = 0.0
            )
            assetRepository.insertAsset(asset)
        }

        // 2. Insert Transaction
        val transaction = Transaction(
            assetSymbol = symbol,
            amount = amount,
            price = price,
            totalValue = amount * price,
            date = System.currentTimeMillis(),
            isBuy = isBuy,
            note = note,
            exchangeRate = exchangeRate
        )
        withContext(Dispatchers.IO) {
            transactionRepository.insertTransaction(transaction)
        }

        // 3. Recalculate Asset state from history
        recalculateAssetUseCase(symbol)
    }
}
