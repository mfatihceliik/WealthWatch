package com.example.wealthwatch.data.local.entity.transaction

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.wealthwatch.data.local.entity.asset.AssetEntity

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = AssetEntity::class,
            parentColumns = ["symbol"],
            childColumns = ["assetSymbol"],
            onDelete = ForeignKey.Companion.CASCADE // Varlık silinirse işlemleri de silinir
        )
    ],
    indices = [Index(value = ["assetSymbol"])]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val assetSymbol: String,
    val amount: Double,       // Alınan miktar (0.5)
    val price: Double,        // İşlem anındaki fiyat (20.000)
    val totalValue: Double,   // İşlem anındaki toplam tutar (amount * price)
    val date: Long = System.currentTimeMillis(),
    val isBuy: Boolean = true, // true: Alım, false: Satım
    val fee: Double = 0.0,
    val note: String = "",
    val exchangeRate: Double = 1.0
)