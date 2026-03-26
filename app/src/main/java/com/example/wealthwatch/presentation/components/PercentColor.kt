package com.example.wealthwatch.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.wealthwatch.domain.model.PriceTrend
import com.example.wealthwatch.ui.theme.AppTheme

@Composable
fun percentColor(
    priceTrend: PriceTrend
): Color {
    val trendColor = when (priceTrend) {
        PriceTrend.UP -> AppTheme.colors.priceUp // Green
        PriceTrend.DOWN -> AppTheme.colors.priceDown // Red
        PriceTrend.NEUTRAL -> AppTheme.colors.priceNeutral // Grey
    }
    return trendColor
}