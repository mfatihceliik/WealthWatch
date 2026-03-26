package com.example.wealthwatch.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wealthwatch.R
import com.example.wealthwatch.presentation.main.topbar.TopBarEvent
import com.example.wealthwatch.core.provider.AppInfoProvider
import com.example.wealthwatch.domain.model.settings.AppCurrency
import com.example.wealthwatch.domain.model.settings.AppLanguage
import com.example.wealthwatch.domain.model.settings.WWTheme
import com.example.wealthwatch.domain.repository.local.settings.SettingsRepository
import com.example.wealthwatch.domain.use_case.currency.SyncCurrencyRatesUseCase
import com.example.wealthwatch.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val syncCurrencyRatesUseCase: SyncCurrencyRatesUseCase
) : BaseViewModel() {

    init {
        setTopBarConfig(
            TopBarEvent.SetConfig(
                isTopbarVisible = true,
                title = R.string.settings_title,
                showBackButton = true
            )
        )
        launch {
            syncCurrencyRatesUseCase()
        }
    }

    val uiState = combine(
        settingsRepository.getTheme(),
        settingsRepository.getLanguage(),
        settingsRepository.getCurrency()
    ) { theme, language, currency ->
        SettingsState(
            theme = theme,
            language = language,
            currency = currency,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsState()
    )

    fun setTheme(theme: WWTheme) = viewModelScope.launch { settingsRepository.setTheme(theme) }
    fun setLanguage(language: AppLanguage) =
        viewModelScope.launch { settingsRepository.setLanguage(language) }

    fun setCurrency(currency: AppCurrency) =
        viewModelScope.launch { settingsRepository.setCurrency(currency) }
}
