package com.example.wealthwatch.presentation.search_asset

import com.example.wealthwatch.presentation.model.AssetUiModel

sealed interface SearchAssetEvent {
    data class OnSearchQueryChange(val query: String) : SearchAssetEvent
    data class OnFilterSelect(val filter: AssetFilter) : SearchAssetEvent
    data class OnAssetClick(val asset: AssetUiModel) : SearchAssetEvent
    data class OnDeleteHistoryItem(val query: String) : SearchAssetEvent
    data object OnClearHistory : SearchAssetEvent
}