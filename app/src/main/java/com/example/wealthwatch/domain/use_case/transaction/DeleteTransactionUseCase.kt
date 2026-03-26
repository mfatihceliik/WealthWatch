package com.example.wealthwatch.domain.use_case.transaction

import com.example.wealthwatch.domain.model.asset.Transaction
import com.example.wealthwatch.domain.repository.local.asset.AssetRepository
import com.example.wealthwatch.domain.repository.local.transaction.TransactionRepository
import com.example.wealthwatch.domain.use_case.asset.RecalculateAssetUseCase
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class DeleteTransactionUseCase @Inject constructor(
    private val assetRepository: AssetRepository,
    private val transactionRepository: TransactionRepository,
    private val recalculateAssetUseCase: RecalculateAssetUseCase
) {
    suspend operator fun invoke(transaction: Transaction) {
        // 1. Delete Transaction
        transactionRepository.deleteTransaction(transaction)

        // 2. Check if any transactions remain for this asset
        val transactions =
            transactionRepository.getTransactionsForAsset(transaction.assetSymbol).firstOrNull()

        if (transactions.isNullOrEmpty()) {
            val asset = assetRepository.getAsset(transaction.assetSymbol)
            if (asset != null) {
                assetRepository.deleteAsset(asset)
            }
        } else {
            // 3. Recalculate
            recalculateAssetUseCase(transaction.assetSymbol)
        }
    }
}
