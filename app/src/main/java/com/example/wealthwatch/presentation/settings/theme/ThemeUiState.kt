package com.example.wealthwatch.presentation.settings.theme

import com.example.wealthwatch.domain.model.settings.WWTheme
import com.example.wealthwatch.presentation.base.BaseUiState
import com.example.wealthwatch.presentation.base.ScreenState

data class ThemeUiState(
    override val screenState: ScreenState = ScreenState.LOADING,
    override val message: String = "",
    val theme: WWTheme = WWTheme.SYSTEM,
    val currentTheme: WWTheme = WWTheme.SYSTEM,
    val availableThemes: List<WWTheme> = WWTheme.entries
) : BaseUiState
