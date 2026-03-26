package com.example.wealthwatch.data.mapper

import com.example.wealthwatch.core.mapper.BaseMapper
import com.example.wealthwatch.data.local.entity.transaction.TransactionEntity
import com.example.wealthwatch.domain.model.asset.Transaction
import javax.inject.Inject

class TransactionMapper @Inject constructor() : BaseMapper<TransactionEntity, Transaction>() {
    override fun map(input: TransactionEntity): Transaction {
        return Transaction(
            id = input.id,
            assetSymbol = input.assetSymbol,
            amount = input.amount,
            price = input.price,
            totalValue = input.totalValue,
            date = input.date,
            isBuy = input.isBuy,
            fee = input.fee,
            note = input.note,
            exchangeRate = input.exchangeRate
        )
    }

    fun mapToEntity(input: Transaction): TransactionEntity {
        return TransactionEntity(
            id = input.id,
            assetSymbol = input.assetSymbol,
            amount = input.amount,
            price = input.price,
            totalValue = input.totalValue,
            date = input.date,
            isBuy = input.isBuy,
            fee = input.fee,
            note = input.note ?: "",
            exchangeRate = input.exchangeRate
        )
    }
}
