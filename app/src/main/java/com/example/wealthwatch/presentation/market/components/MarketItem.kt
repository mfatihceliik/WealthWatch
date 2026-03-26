package com.example.wealthwatch.presentation.market.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.wealthwatch.presentation.components.WWIcon
import com.example.wealthwatch.presentation.components.WWCard
import com.example.wealthwatch.presentation.components.WWText
import com.example.wealthwatch.presentation.components.percentColor
import com.example.wealthwatch.presentation.model.AssetUiModel
import com.example.wealthwatch.ui.theme.AppTheme
@Composable
fun MarketItem(
    modifier: Modifier = Modifier,
    asset: AssetUiModel,
    onClick: () -> Unit
) {
    val spacing = AppTheme.spacing
    val trendColor = percentColor(asset.trend)

    WWCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .padding(spacing.spaceSmall)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spacing.spaceExtraSmall),
                verticalAlignment = Alignment.CenterVertically
            ) {
                WWIcon(
                    modifier = Modifier.size(spacing.defaultIcon),
                    iconUrl = asset.icon,
                    type = asset.type,
                )

                // Sembol ve isim
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    WWText(
                        text = asset.symbol,
                        style = AppTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    WWText(
                        text = asset.name,
                        style = AppTheme.typography.titleSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            // Price
            WWText(
                text = asset.price,
                style = AppTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(spacing.spaceTiny))

            // Footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                WWText(
                    text = asset.priceChange,
                    style = AppTheme.typography.bodySmall,
                    color = trendColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false).padding(end = AppTheme.spacing.spaceExtraSmall)
                )

                // Yüzdelik kutusu
                Box(
                    modifier = Modifier
                        .background(
                            color = trendColor.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(spacing.spaceExtraSmall)
                        )
                        .padding(horizontal = spacing.spaceTiny, vertical = spacing.spaceTiny)
                ) {
                    WWText(
                        text = asset.priceChangePercent,
                        style = AppTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = trendColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
