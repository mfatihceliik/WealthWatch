package com.example.wealthwatch.core.util

import com.example.wealthwatch.domain.model.settings.AppCurrency

interface WealthWatchFormatter {
    fun formatCurrency(amount: Double, currency: AppCurrency): String
    fun formatAmount(amount: Double): String
    fun formatPercentage(percent: Double): String
    fun formatPriceChange(amount: Double, currency: AppCurrency): String
    fun formatProfitLossValue(amount: Double, currency: AppCurrency): String
    fun formatVolume(volume: Double): String
    fun formatCompactVolume(volume: Double): String
    fun formatDistribution(percent: Double): String
}
