package com.example.wealthwatch.presentation.settings

import com.example.wealthwatch.R
import com.example.wealthwatch.core.provider.AppInfoProvider
import com.example.wealthwatch.domain.model.settings.AppCurrency
import com.example.wealthwatch.domain.model.settings.AppLanguage
import com.example.wealthwatch.domain.model.settings.WWTheme
import com.example.wealthwatch.domain.repository.local.settings.SettingsRepository
import com.example.wealthwatch.domain.use_case.currency.SyncExchangeRatesUseCase
import com.example.wealthwatch.presentation.base.BaseViewModel
import com.example.wealthwatch.presentation.main.topbar.TopBarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val appInfoProvider: AppInfoProvider,
    private val syncExchangeRatesUseCase: SyncExchangeRatesUseCase
) : BaseViewModel() {

    private val _settingsState: MutableStateFlow<SettingsState> = MutableStateFlow(SettingsState())
    val settingsState: StateFlow<SettingsState> = _settingsState.asStateFlow()

    init {
        setTopbarConfig()
        observeSettings()
        launch {
            syncExchangeRatesUseCase()
        }
    }

    private fun setTopbarConfig() = setTopBarConfig(
        TopBarEvent.SetConfig(
            isTopbarVisible = true, title = R.string.settings_title, showBackButton = true
        )
    )

    private fun observeSettings() {
        launch {
            combine(
                settingsRepository.getTheme(),
                settingsRepository.getLanguage(),
                settingsRepository.getCurrency(),
            ) { theme, language, currency ->
                _settingsState.update {
                    it.copy(
                        theme = theme,
                        language = language,
                        currency = currency,
                        appVersion = appInfoProvider.versionName
                    )
                }
            }.collect {}
        }
    }

    fun setTheme(theme: WWTheme) = launch { settingsRepository.setTheme(theme) }
    fun setLanguage(language: AppLanguage) = launch { settingsRepository.setLanguage(language) }
    fun setCurrency(currency: AppCurrency) = launch { settingsRepository.setCurrency(currency) }
}
