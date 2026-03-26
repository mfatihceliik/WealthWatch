package com.example.wealthwatch.presentation.search_asset

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wealthwatch.R
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.presentation.common.BaseScreen
import com.example.wealthwatch.presentation.components.SearchHistoryContent
import com.example.wealthwatch.presentation.main.topbar.TopBarEvent
import com.example.wealthwatch.presentation.main.topbar.TopBarViewModel
import com.example.wealthwatch.presentation.market.components.MarketRowSection
import com.example.wealthwatch.presentation.search_asset.components.AssetFilterChips
import com.example.wealthwatch.presentation.search_asset.components.suggestedAssetsSection
import com.example.wealthwatch.ui.theme.AppTheme

@Composable
fun SearchAssetScreen(
    onNavigateToDetail: (String, AssetType) -> Unit,
    viewModel: SearchAssetViewModel = hiltViewModel(),
    topBarViewModel: TopBarViewModel = hiltViewModel(LocalActivity.current as ComponentActivity)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val topBarState by topBarViewModel.state.collectAsStateWithLifecycle()

    // Sync query from TopBar to ViewModel
    LaunchedEffect(topBarState.query) {
        viewModel.onEvent(SearchAssetEvent.OnSearchQueryChange(topBarState.query))
    }

    // Configure TopBar on entry
    BaseScreen(
        state = uiState, viewModel = viewModel
    ) {
        val spacing = AppTheme.spacing
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = spacing.spaceSmall),
            verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {

            if (uiState.searchQuery.isEmpty()) {
                // Show filters only when not searching/focused
                if (!topBarState.isSearchFocused) {
                    item(key = "filters") {
                        AssetFilterChips(
                            selectedFilter = uiState.selectedFilter,
                            onFilterSelect = { viewModel.onEvent(SearchAssetEvent.OnFilterSelect(it)) },
                        )
                    }
                }

                // Show History only when focused
                if (topBarState.isSearchFocused && uiState.searchHistory.isNotEmpty()) {
                    item(key = "history") {
                        SearchHistoryContent(
                            searchHistory = uiState.searchHistory,
                            onHistoryItemClick = { query ->
                                topBarViewModel.onEvent(TopBarEvent.OnQueryChanged(query))
                            },
                            onDeleteHistoryItem = { query ->
                                viewModel.onEvent(SearchAssetEvent.OnDeleteHistoryItem(query))
                            },
                            onClearAllHistory = {
                                viewModel.onEvent(SearchAssetEvent.OnClearHistory)
                            })
                    }
                }

                // Show Dashboard if NOT focused
                if (!topBarState.isSearchFocused) {
                    if (uiState.topMovers.isNotEmpty()) {
                        item(key = "dashboard") {
                            MarketRowSection(
                                title = stringResource(R.string.top_movers),
                                assets = uiState.topMovers,
                                onItemClick = { asset ->
                                    viewModel.onEvent(SearchAssetEvent.OnAssetClick(asset))
                                    onNavigateToDetail(asset.symbol, asset.type)
                                })
                        }
                    }

                    if (uiState.suggestedAssets.isNotEmpty()) {
                        suggestedAssetsSection(
                            assets = uiState.suggestedAssets,
                            onAssetClick = { asset ->
                                viewModel.onEvent(SearchAssetEvent.OnAssetClick(asset))
                                onNavigateToDetail(asset.symbol, asset.type)
                            }
                        )
                    }
                }
            } else {
                // Show Search Results
                suggestedAssetsSection(
                    assets = uiState.searchResults,
                    onAssetClick = { asset ->
                        viewModel.onEvent(SearchAssetEvent.OnAssetClick(asset))
                        onNavigateToDetail(asset.symbol, asset.type)
                    }
                )
            }
        }
    }
}


