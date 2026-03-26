package com.example.wealthwatch.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.wealthwatch.R
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.presentation.model.AssetUiModel
import com.example.wealthwatch.ui.theme.AppTheme
import com.example.wealthwatch.ui.theme.LocalSpacing

@Composable
fun AssetListItem(
    modifier: Modifier = Modifier,
    item: AssetUiModel,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit
) {
    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.spaceSmall)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = spacing.spaceSmall)
            .clip(shape)
            .background(AppTheme.colors.surface)
            .clickable(onClick = onClick)
            .padding(spacing.spaceMedium), verticalAlignment = Alignment.CenterVertically
    ) {
        // --- LEFT SECTION (Icon + Symbol + Name) ---
        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
            WWIcon(
                modifier = Modifier.size(spacing.spaceHuge),
                type = item.type,
                iconUrl = item.icon
            )

            Spacer(modifier = Modifier.width(spacing.spaceMedium))

            Column {
                WWText(
                    text = item.symbol,
                    style = AppTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                WWText(
                    text = item.name,
                    style = AppTheme.typography.bodySmall,
                    color = AppTheme.colors.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    WWText(
                        text = stringResource(R.string.volume),
                        style = AppTheme.typography.labelSmall,
                        color = AppTheme.colors.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.width(spacing.spaceTiny))
                    WWText(
                        text = item.volume,
                        style = AppTheme.typography.labelSmall,
                        color = AppTheme.colors.onSurfaceVariant
                    )
                }
            }
        }

        // --- PRICE SECTION ---
        Column(horizontalAlignment = Alignment.End) {
            WWText(
                text = item.price,
                style = AppTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = AppTheme.colors.onSurface,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))

                val trendColor = percentColor(item.trend)
                val formattedChange = "% ${item.priceChangePercent.replace("-", "").replace("+", "")}"

                WWText(
                    text = formattedChange,
                    style = AppTheme.typography.labelSmall,
                    color = trendColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(
                            color = trendColor.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(spacing.spaceSmall)
                        )
                        .padding(horizontal = spacing.spaceExtraSmall, vertical = spacing.spaceTiny)
                )
        }

        Spacer(modifier = Modifier.width(spacing.spaceSmall))

        // --- ACTION SECTION ---
        IconButton(
            onClick = onToggleFavorite, modifier = Modifier.size(spacing.spaceLarge)
        ) {
            WWIcon(
                imageVector = if (isFavorite) Icons.Filled.Star else Icons.Filled.FavoriteBorder,
                tint = if (isFavorite) AppTheme.colors.primary
                else AppTheme.colors.onSurfaceVariant
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
private fun MarketItemPreview() {
    com.example.wealthwatch.ui.theme.WealthWatchTheme {
        AssetListItem(
            item = AssetUiModel(
                symbol = "AAPL",
                name = "Apple Inc.",
                type = AssetType.US_STOCK,
                price = "$175.50",
                priceChangePercent = "1.25",
                volume = "50M",
                trend = com.example.wealthwatch.domain.model.PriceTrend.UP
            ),
            isFavorite = true,
            onToggleFavorite = {},
            onClick = {}
        )
    }
}
