package com.example.wealthwatch.presentation.watchlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.presentation.common.BaseScreen
import com.example.wealthwatch.presentation.components.AssetListItem

@Composable
fun WatchlistScreen(
    viewModel: WatchlistViewModel = hiltViewModel(), onNavigateToDetail: (String, AssetType) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BaseScreen(
        state = uiState,
        onRetry = { },
        viewModel = viewModel
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(items = uiState.cryptoList, key = { it.symbol }) { crypto ->
                AssetListItem(
                    item = crypto,
                    isFavorite = true, // In watchlist, it's always favorite
                    onToggleFavorite = { viewModel.toggleFavorite(crypto) },
                    onClick = { onNavigateToDetail(crypto.symbol, crypto.type) })
            }
        }
    }
}
