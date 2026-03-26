package com.example.wealthwatch.presentation.search_asset.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.wealthwatch.presentation.model.AssetUiModel
import com.example.wealthwatch.presentation.components.WWText
import com.example.wealthwatch.presentation.components.percentColor
import com.example.wealthwatch.presentation.search_asset.AssetFilter
import com.example.wealthwatch.ui.theme.AppTheme
import com.example.wealthwatch.presentation.components.WWIcon
import androidx.compose.ui.layout.ContentScale

@Composable
fun AssetFilterChips(
    selectedFilter: AssetFilter,
    onFilterSelect: (AssetFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.spaceSmall),
        contentPadding = PaddingValues(horizontal = AppTheme.spacing.spaceMedium)
    ) {
        items(AssetFilter.entries, key = { it.name }) { filter ->
            val isSelected = filter == selectedFilter
            FilterChip(
                selected = isSelected,
                onClick = { onFilterSelect(filter) },
                label = {
                    WWText(
                        text = filter.label,
                        style = AppTheme.typography.labelLarge,
                        color = if (isSelected) Color.Black else AppTheme.colors.text
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = AppTheme.colors.card,
                    selectedContainerColor = AppTheme.colors.primary,
                    labelColor = AppTheme.colors.text,
                    selectedLabelColor = Color.Black
                ),
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = Color.Transparent,
                    selectedBorderColor = Color.Transparent,
                    enabled = true,
                    selected = isSelected
                ),
                shape = RoundedCornerShape(50)
            )
        }
    }
}

// Replaced SuggestedAssetsSection with LazyListScope extension
fun LazyListScope.suggestedAssetsSection(
    assets: List<AssetUiModel>,
    onAssetClick: (AssetUiModel) -> Unit
) {
    if (assets.isEmpty()) return

    item(key = "suggested_assets_title") {
        WWText(
            text = "SUGGESTED ASSETS",
            style = AppTheme.typography.labelLarge,
            color = AppTheme.colors.onSurfaceVariant,
            modifier = Modifier
                .padding(horizontal = AppTheme.spacing.spaceMedium)
                .padding(bottom = AppTheme.spacing.spaceSmall)
        )
    }

    items(assets, key = { it.symbol }) { asset ->
        Box(modifier = Modifier.padding(horizontal = AppTheme.spacing.spaceMedium)) {
            SuggestedAssetItem(asset = asset, onClick = { onAssetClick(asset) })
        }
    }
}

@Composable
fun SuggestedAssetItem(
    asset: AssetUiModel,
    onClick: () -> Unit
) {
    val changeColor = percentColor(asset.trend)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = AppTheme.spacing.cardElevation,
                shape = RoundedCornerShape(AppTheme.spacing.containerCornerRadius),
                spotColor = AppTheme.colors.onSurface.copy(alpha = 0.1f)
            )
            .clip(RoundedCornerShape(AppTheme.spacing.containerCornerRadius))
            .background(AppTheme.colors.card)
            .clickable(onClick = onClick)
            .padding(
                start = AppTheme.spacing.spaceMedium,
                top = AppTheme.spacing.spaceMedium,
                bottom = AppTheme.spacing.spaceMedium,
                end = AppTheme.spacing.spaceSmall
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        WWIcon(
            url = asset.icon,
            contentDescription = asset.symbol,
            modifier = Modifier
                .size(AppTheme.spacing.iconMedium)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(AppTheme.spacing.spaceMedium))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            WWText(
                text = asset.symbol,
                style = AppTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            WWText(
                text = asset.name,
                style = AppTheme.typography.bodySmall,
                color = AppTheme.colors.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.weight(0.8f)
        ) {
            WWText(
                text = asset.price,
                style = AppTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            WWText(
                text = asset.priceChangePercent,
                style = AppTheme.typography.labelMedium,
                color = changeColor,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(AppTheme.spacing.spaceSmall))

        IconButton(
            onClick = { /* Add to watchlist logic */ },
            modifier = Modifier.size(AppTheme.spacing.iconMedium)
        ) {
            WWIcon(
                imageVector = Icons.Default.Star,
                tint = AppTheme.colors.outline
            )
        }
    }
}
