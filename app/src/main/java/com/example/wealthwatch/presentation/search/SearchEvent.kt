package com.example.wealthwatch.presentation.search

import com.example.wealthwatch.presentation.model.AssetUiModel

sealed interface SearchEvent {
    data class OnQueryChanged(val query: String) : SearchEvent
    object OnClearQuery : SearchEvent
    data class OnHistoryItemClicked(val query: String) : SearchEvent
    data class OnDeleteHistoryItem(val query: String) : SearchEvent
    object OnClearAllHistory : SearchEvent
    data class OnToggleFavorite(val crypto: AssetUiModel) : SearchEvent
}
