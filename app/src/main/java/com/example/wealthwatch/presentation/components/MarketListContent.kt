package com.example.wealthwatch.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.wealthwatch.domain.model.PriceTrend
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.presentation.model.AssetUiModel
import com.example.wealthwatch.ui.theme.LocalSpacing
import com.example.wealthwatch.ui.theme.WealthWatchTheme

@Composable
fun MarketListContent(
    cryptoList: List<AssetUiModel>,
    favorites: Set<String>,
    onToggleFavorite: (AssetUiModel) -> Unit,
    onItemClick: (AssetUiModel) -> Unit
) {
    val spacing = LocalSpacing.current // Move outside loop
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = spacing.spaceLarge)
    ) {
        items(items = cryptoList, key = { it.symbol }, contentType = { "crypto_item" }) { crypto ->
            // Stabilize lambdas to prevent unnecessary recomposition

            AssetListItem(
                item = crypto,
                isFavorite = favorites.contains(crypto.symbol),
                onToggleFavorite = { onToggleFavorite(crypto) },
                onClick = { onItemClick(crypto) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.spaceMedium)
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
private fun MarketListContentPreview() {
    WealthWatchTheme {
        MarketListContent(
            cryptoList = listOf(
                AssetUiModel(
                    symbol = "BTC",
                    name = "Bitcoin",
                    type = AssetType.CRYPTO,
                    price = "$45,000",
                    priceChangePercent = "2.5",
                    trend = PriceTrend.UP
                ),
                AssetUiModel(
                    symbol = "ETH",
                    name = "Ethereum",
                    type = AssetType.CRYPTO,
                    price = "$3,200",
                    priceChangePercent = "1.8",
                    trend = PriceTrend.UP
                ),
                AssetUiModel(
                    symbol = "SOL",
                    name = "Solana",
                    type = AssetType.CRYPTO,
                    price = "$110",
                    priceChangePercent = "-5.0",
                    trend = PriceTrend.DOWN
                )
            ),
            favorites = setOf("BTC"),
            onToggleFavorite = {},
            onItemClick = {}
        )
    }
}
