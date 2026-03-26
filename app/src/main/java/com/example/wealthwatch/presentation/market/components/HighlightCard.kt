package com.example.wealthwatch.presentation.market.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.wealthwatch.presentation.components.WWCard
import com.example.wealthwatch.presentation.components.WWText
import com.example.wealthwatch.presentation.components.percentColor
import com.example.wealthwatch.presentation.model.AssetUiModel
import com.example.wealthwatch.ui.theme.AppTheme

@Composable
fun HighlightCard(
    asset: AssetUiModel, onClick: () -> Unit
) {
    val spacing = AppTheme.spacing
    val trendColor = percentColor(asset.trend)

    WWCard(
        modifier = Modifier
            .width(160.dp)
            .height(120.dp),
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(spacing.spaceSmall),
            verticalArrangement = Arrangement.spacedBy(spacing.spaceTiny)
        ) {
            // Row 1: Name and Percent
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                WWText(
                    text = asset.symbol, // e.g. Gold, BTC
                    style = AppTheme.typography.labelLarge,
                    color = AppTheme.colors.onSurfaceVariant
                )

                Box(
                    modifier = Modifier
                        .background(
                            color = trendColor.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(AppTheme.spacing.spaceExtraSmall)
                        )
                        .padding(horizontal = AppTheme.spacing.spaceExtraSmall, vertical = AppTheme.spacing.spaceTiny)
                ) {
                    WWText(
                        text = asset.priceChangePercent,
                        style = AppTheme.typography.labelSmall,
                        color = trendColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Price directly below
            WWText(
                text = asset.price,
                style = AppTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = AppTheme.colors.onSurface
            )

            // Sparkline at the bottom, filling remaining height
            SimpleSparkLine(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // Take all remaining space
                color = trendColor,
                priceChangePercent = asset.priceChangePercent,
                isFilled = true
            )
        }
    }
}
