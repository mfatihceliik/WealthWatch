package com.example.wealthwatch.data.local.entity.asset

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wealthwatch.domain.model.asset.AssetType

@Entity(tableName = "assets")
data class AssetEntity(
    @PrimaryKey val symbol: String,
    val name: String = "",
    val icon: String = "",
    val type: AssetType = AssetType.OTHER,
    val totalAmount: Double = 0.0,
    val averageCost: Double = 0.0,
    val currentPrice: Double = 0.0 // Kept as cached last seen price
)

