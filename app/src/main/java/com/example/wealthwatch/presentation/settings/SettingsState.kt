package com.example.wealthwatch.presentation.settings

import com.example.wealthwatch.domain.model.settings.AppCurrency
import com.example.wealthwatch.domain.model.settings.AppLanguage
import com.example.wealthwatch.domain.model.settings.WWTheme
import com.example.wealthwatch.presentation.base.BaseUiState
import com.example.wealthwatch.presentation.base.ScreenState

data class SettingsState(
    override val screenState: ScreenState = ScreenState.HAS_DATA,
    override val message: String = "",
    val language: AppLanguage = AppLanguage.ENGLISH,
    val userName: String = "",
    val userEmail: String = "",
    val theme: WWTheme = WWTheme.SYSTEM,
    val appVersion: String = "",
    val currency: AppCurrency = AppCurrency.USD,
    val isNotificationsEnabled: Boolean = false,
    val isBiometricEnabled: Boolean = false
) : BaseUiState
