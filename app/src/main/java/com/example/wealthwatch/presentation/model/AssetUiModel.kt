package com.example.wealthwatch.presentation.model

import androidx.compose.runtime.Immutable
import com.example.wealthwatch.domain.model.PriceTrend
import com.example.wealthwatch.domain.model.asset.AssetType


@Immutable
data class AssetUiModel(
    val symbol: String = "",
    val name: String = "",
    val icon: String = "",
    val type: AssetType = AssetType.CRYPTO,
    val amount: Double = 0.0,
    val formattedAmount: String = "",
    val formattedTotalValue: String = "",
    val formattedAverageCost: String = "",
    val formattedProfitLoss: String = "", // % Change
    val formattedProfitLossValue: String = "", // Value Change
    val isProfit: Boolean = false,
    // Market Data Fields
    val priceChange: String = "0.0",
    val priceChangePercent: String = "0.0",
    val volume: String = "",
    val quoteVolume: String = "",
    val price: String = "",
    val trend: PriceTrend = PriceTrend.NEUTRAL,
    val isFavorite: Boolean = false
)
