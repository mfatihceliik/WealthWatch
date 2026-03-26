package com.example.wealthwatch.presentation.settings.language

import androidx.lifecycle.viewModelScope
import com.example.wealthwatch.R
import com.example.wealthwatch.domain.model.settings.AppLanguage
import com.example.wealthwatch.domain.repository.local.settings.SettingsRepository
import com.example.wealthwatch.presentation.base.BaseViewModel
import com.example.wealthwatch.presentation.base.BaseUiState
import com.example.wealthwatch.presentation.base.ScreenState
import com.example.wealthwatch.presentation.main.topbar.TopBarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(LanguageUiState())
    val uiState: StateFlow<LanguageUiState> = _uiState.asStateFlow()

    init {
        setTopBarConfig(
            TopBarEvent.SetConfig(
                isTopbarVisible = true,
                title = R.string.settings_language,
                showBackButton = true
            )
        )
        observeLanguage()
    }

    private fun observeLanguage() {
        viewModelScope.launch {
            settingsRepository.getLanguage().collect { language ->
                _uiState.update { it.copy(language = language) }
            }
        }
    }

    fun setLanguage(language: AppLanguage) {
        viewModelScope.launch {
            settingsRepository.setLanguage(language)
        }
    }
}