package com.example.wealthwatch.presentation.main

import com.example.wealthwatch.presentation.base.BaseUiState
import com.example.wealthwatch.presentation.base.ScreenState

data class MainUiState(
    override val screenState: ScreenState = ScreenState.LOADING,
    override val message: String = "",
    val isLocked: Boolean = false
) : BaseUiState

