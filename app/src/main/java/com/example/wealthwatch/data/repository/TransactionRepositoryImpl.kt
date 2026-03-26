package com.example.wealthwatch.data.repository

import com.example.wealthwatch.data.local.LocalDataSource
import com.example.wealthwatch.data.mapper.TransactionMapper
import com.example.wealthwatch.domain.model.asset.Transaction
import com.example.wealthwatch.domain.repository.local.transaction.TransactionRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl @Inject constructor(
    private val local: LocalDataSource,
    private val transactionMapper: TransactionMapper
) : TransactionRepository {

    override fun getTransactionsForAsset(symbol: String): Flow<List<Transaction>> {
        return local.getAssetHistory(symbol).map { assetWithTransactions ->
            val transactions = assetWithTransactions?.transactions ?: emptyList()
            transactionMapper(transactions)
        }
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        local.insertTransaction(transactionMapper.mapToEntity(transaction))
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        local.deleteTransaction(transactionMapper.mapToEntity(transaction))
    }

    override suspend fun getTransactionsList(symbol: String): List<Transaction> {
        return local.getTransactionsBySymbol(symbol).let {
            transactionMapper(it)
        }
    }
}
