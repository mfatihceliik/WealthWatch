package com.example.wealthwatch.presentation.main.topbar

sealed interface TopBarEvent {
    data class OnQueryChanged(val query: String) : TopBarEvent
    data object OnClearQuery : TopBarEvent
    data class SetConfig(
        val isTopbarVisible: Boolean = false,
        val title: Int? = null,
        val titleString: String? = null,
        val showBackButton: Boolean = false,
        val isSearchVisible: Boolean = false
    ) : TopBarEvent

    data class OnSearchFocusChanged(val isFocused: Boolean) : TopBarEvent
}
