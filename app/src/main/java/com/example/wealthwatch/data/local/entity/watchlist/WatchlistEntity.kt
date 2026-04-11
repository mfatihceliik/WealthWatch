package com.example.wealthwatch.data.local.entity.watchlist

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wealthwatch.domain.model.asset.AssetType

@Entity(tableName = "watchlist")
data class WatchlistEntity(
    @PrimaryKey val symbol: String,
    val name: String = "",
    val assetType: AssetType = AssetType.EXCHANGE,
    val iconUrl: String = "",
    val price: String = "0.00",
    val priceChangePercent: String = "0.00",
    val volume: String = "",
    val addedAt: Long = System.currentTimeMillis()
)