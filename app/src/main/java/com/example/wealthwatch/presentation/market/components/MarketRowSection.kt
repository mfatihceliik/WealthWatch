package com.example.wealthwatch.presentation.market.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.presentation.components.WWText
import com.example.wealthwatch.presentation.market.SectionType
import com.example.wealthwatch.presentation.model.AssetUiModel
import com.example.wealthwatch.ui.theme.AppTheme

@Composable
fun MarketRowSection(
    modifier: Modifier = Modifier,
    title: String,
    assets: List<AssetUiModel>,
    onItemClick: (AssetUiModel) -> Unit,
    sectionType: SectionType = SectionType.Standard
) {
    if (assets.isEmpty()) return

    val titleColor = when (sectionType) {
        SectionType.Gainer -> AppTheme.colors.priceUp
        SectionType.Loser -> AppTheme.colors.priceDown
        else -> AppTheme.colors.text
    }

    val titleIcon = when (sectionType) {
        SectionType.Gainer -> Icons.Default.ArrowUpward
        SectionType.Loser -> Icons.Default.ArrowDownward
        else -> null
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppTheme.spacing.spaceMedium,
                    vertical = AppTheme.spacing.spaceSmall
                ),
                //.padding(vertical = WWTheme.spacing.spaceSmall),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.spaceSmall)
        ) {
            WWText(
                text = title,
                style = AppTheme.typography.bodyLarge,
                color = titleColor,
            )
            if (titleIcon != null) {
                Icon(
                    imageVector = titleIcon,
                    contentDescription = null,
                    tint = titleColor,
                    modifier = Modifier.size(AppTheme.spacing.spaceMedium)
                )
            }
        }
        LazyRow(
            contentPadding = PaddingValues(AppTheme.spacing.default),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.spaceSmall)
        ) {
            items(items = assets, key = { it.symbol }) { asset ->
                MarketItem(
                    modifier = Modifier.width(160.dp),
                    asset = asset,
                    onClick = { onItemClick(asset) }
                )
            }
        }
    }
}