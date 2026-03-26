package com.example.wealthwatch.presentation.market.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wealthwatch.presentation.components.WWText
import com.example.wealthwatch.presentation.model.AssetUiModel
import com.example.wealthwatch.ui.theme.AppTheme
import com.example.wealthwatch.ui.theme.LocalSpacing

@Composable
fun MarketContent(
    modifier: Modifier = Modifier,
    title: String,
    assets: List<AssetUiModel>,
    onItemClick: (AssetUiModel) -> Unit,
) {
    val spacing = LocalSpacing.current
    if (assets.isEmpty()) return

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        WWText(
            text = title,
            style = AppTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = spacing.spaceMedium)
        )
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        LazyRow(
            contentPadding = PaddingValues(horizontal = spacing.spaceSmall),
            horizontalArrangement = Arrangement.spacedBy(spacing.spaceSmall)
        ) {
            items(assets, key = { it.symbol }) { asset ->
                MarketItem(
                    modifier = Modifier.width(200.dp),
                    asset = asset,
                    onClick = { onItemClick(asset) }
                )
            }
        }
    }
}