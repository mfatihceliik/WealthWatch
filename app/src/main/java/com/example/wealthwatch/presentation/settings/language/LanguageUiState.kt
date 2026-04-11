package com.example.wealthwatch.presentation.settings.language

import com.example.wealthwatch.domain.model.settings.AppLanguage
import com.example.wealthwatch.presentation.base.BaseUiState
import com.example.wealthwatch.presentation.base.ScreenState

data class LanguageUiState(
    override val screenState: ScreenState = ScreenState.HAS_DATA,
    override val message: String = "",
    val language: AppLanguage = AppLanguage.ENGLISH,
    val availableLanguages: List<AppLanguage> = AppLanguage.entries
) : BaseUiState
