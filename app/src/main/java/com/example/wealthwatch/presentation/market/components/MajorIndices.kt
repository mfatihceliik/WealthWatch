package com.example.wealthwatch.presentation.market.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.wealthwatch.presentation.components.WWText
import com.example.wealthwatch.presentation.model.AssetUiModel
import com.example.wealthwatch.ui.theme.AppTheme

@Composable
fun MajorIndices(
    modifier: Modifier = Modifier,
    indices: List<AssetUiModel>,
    onItemClick: (AssetUiModel) -> Unit
) {
    val spacing = AppTheme.spacing

    Column(modifier = modifier.fillMaxWidth()) {
        WWText(
            text = "Major Indices",
            style = AppTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = AppTheme.colors.onSurface,
        )

        Spacer(modifier = Modifier.height(spacing.spaceSmall))

        LazyRow(
            contentPadding = PaddingValues(horizontal = spacing.default),
            horizontalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {
            items(indices) { asset ->
                IndexCard(
                    asset = asset, onClick = { onItemClick(asset) }
                )
            }
        }
    }
}
