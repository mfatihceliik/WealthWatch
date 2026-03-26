package com.example.wealthwatch.domain.use_case.asset

import com.example.wealthwatch.domain.repository.local.asset.AssetRepository
import com.example.wealthwatch.domain.repository.local.transaction.TransactionRepository
import jakarta.inject.Inject

class RecalculateAssetUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val assetRepository: AssetRepository
) {
    suspend operator fun invoke(symbol: String) {
        val transactions = transactionRepository.getTransactionsList(symbol)

        var currentAvgCost = 0.0
        var currentAmount = 0.0

        transactions.sortedBy { it.date }.forEach { transaction ->
            if (transaction.isBuy) {
                val totalValueBefore = currentAmount * currentAvgCost
                val transactionValue = transaction.amount * transaction.price

                currentAmount += transaction.amount
                if (currentAmount > 0) {
                    currentAvgCost = (totalValueBefore + transactionValue) / currentAmount
                }
            } else {
                currentAmount -= transaction.amount
                if (currentAmount < 0) currentAmount = 0.0
            }
        }
        
        android.util.Log.d(
            "AssetDetailDebug",
            "Recalculate: Found ${transactions.size} transactions for $symbol. New Amount=$currentAmount"
        )

        val existingAsset = assetRepository.getAsset(symbol) ?: return

        assetRepository.updateAsset(
            existingAsset.copy(
                totalAmount = currentAmount, averageCost = currentAvgCost
            )
        )
    }
}
