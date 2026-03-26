package com.example.wealthwatch.domain.repository.local.transaction

import com.example.wealthwatch.domain.model.asset.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transaction: Transaction)
    suspend fun getTransactionsList(symbol: String): List<Transaction>
    fun getTransactionsForAsset(symbol: String): Flow<List<Transaction>>
}