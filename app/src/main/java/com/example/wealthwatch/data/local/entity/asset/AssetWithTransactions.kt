package com.example.wealthwatch.data.local.entity.asset

import androidx.room.Embedded
import androidx.room.Relation
import com.example.wealthwatch.data.local.entity.transaction.TransactionEntity

data class AssetWithTransactions(
    @Embedded val asset: AssetEntity,
    @Relation(
        parentColumn = "symbol",      // AssetEntity içindeki @PrimaryKey olan alan
        entityColumn = "assetSymbol"  // TransactionEntity içindeki yabancı anahtar (FK)
    )
    val transactions: List<TransactionEntity>
)