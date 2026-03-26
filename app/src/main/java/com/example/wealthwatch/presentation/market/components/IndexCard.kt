package com.example.wealthwatch.presentation.market.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.wealthwatch.domain.model.PriceTrend
import com.example.wealthwatch.presentation.components.WWCard
import com.example.wealthwatch.presentation.components.WWText
import com.example.wealthwatch.presentation.components.percentColor
import com.example.wealthwatch.presentation.model.AssetUiModel
import com.example.wealthwatch.ui.theme.AppTheme

@Composable
fun IndexCard(
    asset: AssetUiModel,
    onClick: () -> Unit
) {
    val spacing = AppTheme.spacing
    val trendColor = percentColor(asset.trend)

    // Status Text and Indicator Logic (Mock logic for UI demo as requested)
    val statusText = if(asset.trend == PriceTrend.UP) "Bullish Market" else "High Volatility"
    val indicatorColor1 = if(asset.trend == PriceTrend.UP) AppTheme.colors.success else AppTheme.colors.error
    val indicatorColor2 = if(asset.trend == PriceTrend.UP) AppTheme.colors.success.copy(alpha=0.3f) else AppTheme.colors.onSurface.copy(alpha=0.3f)


    WWCard(
        modifier = Modifier
            .width(180.dp)
            .height(130.dp), onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(spacing.spaceSmall),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Title Row with colored bar
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(width = 4.dp, height = 14.dp)
                        .background(color = AppTheme.colors.primary, shape = RoundedCornerShape(2.dp))
                )
                Spacer(modifier = Modifier.width(spacing.spaceSmall))
                WWText(
                    text = asset.name.ifEmpty { asset.symbol },
                    style = AppTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colors.onSurface
                )
            }

            Spacer(modifier = Modifier.height(spacing.spaceSmall))

            // Value and Percent
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(spacing.spaceSmall)
            ) {
                WWText(
                    text = asset.price,
                    style = AppTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colors.onSurface
                )

                WWText(
                    text = asset.priceChangePercent,
                    style = AppTheme.typography.labelSmall,
                    color = trendColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Bottom Status Bar
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().height(4.dp).background(Color.Gray.copy(alpha=0.1f), RoundedCornerShape(2.dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(0.6f)
                            .fillMaxSize()
                            .background(color = indicatorColor1, shape = RoundedCornerShape(topStart = 2.dp, bottomStart = 2.dp))
                    )
                    Box(
                        modifier = Modifier
                            .weight(0.4f)
                            .fillMaxSize()
                            .background(color = indicatorColor2, shape = RoundedCornerShape(topEnd = 2.dp, bottomEnd = 2.dp))
                    )
                }

                Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))

                WWText(
                    text = statusText,
                    style = AppTheme.typography.labelSmall,
                    color = AppTheme.colors.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        }
    }
}
