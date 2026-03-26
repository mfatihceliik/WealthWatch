package com.example.wealthwatch.presentation.search_asset

import com.example.wealthwatch.presentation.base.BaseUiState
import com.example.wealthwatch.presentation.base.ScreenState
import com.example.wealthwatch.presentation.model.AssetUiModel

data class SearchAssetUiState(
    override val screenState: ScreenState = ScreenState.LOADING,
    override val message: String = "",
    val searchQuery: String = "",
    val selectedFilter: AssetFilter = AssetFilter.ALL,
    val topMovers: List<AssetUiModel> = emptyList(),
    val suggestedAssets: List<AssetUiModel> = emptyList(),
    val searchResults: List<AssetUiModel> = emptyList(),
    val searchHistory: List<String> = emptyList()
) : BaseUiState


