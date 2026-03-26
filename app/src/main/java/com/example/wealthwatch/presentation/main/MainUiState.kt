package com.example.wealthwatch.presentation.main

import com.example.wealthwatch.domain.model.settings.AppLanguage
import com.example.wealthwatch.domain.model.settings.WWTheme
import com.example.wealthwatch.presentation.base.BaseUiState
import com.example.wealthwatch.presentation.base.ScreenState

data class MainUiState(
    override val screenState: ScreenState = ScreenState.HAS_DATA,
    override val message: String = "",
    val theme: WWTheme = WWTheme.SYSTEM,
    val language: AppLanguage = AppLanguage.ENGLISH,
    val isLocked: Boolean = false // Default to false, check in VM
): BaseUiState
