package com.example.wealthwatch.presentation.main.topbar

import com.example.wealthwatch.presentation.base.BaseUiState
import com.example.wealthwatch.presentation.base.ScreenState

data class TopBarState(
    override val screenState: ScreenState = ScreenState.HAS_DATA,
    override val message: String = "",
    val isVisible: Boolean = false,
    val titleResId: Int? = null,
    val showBackButton: Boolean = false,
    val query: String = "",
    val isSearchVisible: Boolean = false,
    val isSearchFocused: Boolean = false,
    val isTopbarVisible: Boolean = false,
    val titleString: String? = null
): BaseUiState
