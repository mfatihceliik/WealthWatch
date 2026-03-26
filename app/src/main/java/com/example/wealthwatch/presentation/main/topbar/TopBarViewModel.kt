package com.example.wealthwatch.presentation.main.topbar

import com.example.wealthwatch.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TopBarViewModel @Inject constructor() : BaseViewModel() {
    private val _state = MutableStateFlow(TopBarState())
    val state: StateFlow<TopBarState> = _state.asStateFlow()

    fun onEvent(event: TopBarEvent) {
        when (event) {
            is TopBarEvent.OnQueryChanged -> _state.update { it.copy(query = event.query) }

            TopBarEvent.OnClearQuery -> _state.update { it.copy(query = "") }

            is TopBarEvent.SetConfig -> _state.update {
                it.copy(
                    isTopbarVisible = event.isTopbarVisible,
                    titleResId = event.title,
                    titleString = event.titleString,
                    showBackButton = event.showBackButton,
                    isSearchVisible = event.isSearchVisible
                )
            }

            is TopBarEvent.OnSearchFocusChanged -> _state.update { it.copy(isSearchFocused = event.isFocused) }
        }
    }
}

