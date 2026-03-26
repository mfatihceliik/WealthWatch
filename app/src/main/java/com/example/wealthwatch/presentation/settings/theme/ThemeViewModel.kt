package com.example.wealthwatch.presentation.settings.theme

import androidx.lifecycle.viewModelScope
import com.example.wealthwatch.R
import com.example.wealthwatch.domain.model.settings.WWTheme
import com.example.wealthwatch.domain.repository.local.settings.SettingsRepository
import com.example.wealthwatch.presentation.base.BaseViewModel
import com.example.wealthwatch.presentation.main.topbar.TopBarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(ThemeUiState()) // Need to define ThemeUiState or reuse SettingsState? 
    // Wait, ThemeScreen uses `uiState.theme` and `uiState.availableThemes`.
    // SettingsViewModel used SettingsState.
    // I should create a specific state or reuse a simple one.
    // Let's create `ThemeState` inside this file or strictly typed.
    // Actually, `ThemeScreen` usage: `uiState.availableThemes`, `uiState.theme`.
    // availableThemes is likely static list of WWTheme.entries.
    
    // Let's modify ThemeScreen to expect `ThemeUiState`.
    
    val uiState: StateFlow<ThemeUiState> = _uiState.asStateFlow()

    init {
        setTopBarConfig(
            TopBarEvent.SetConfig(
                isTopbarVisible = true,
                title = R.string.settings_theme,
                showBackButton = true
            )
        )
        observeTheme()
    }

    private fun observeTheme() {
        viewModelScope.launch {
            settingsRepository.getTheme().collect { theme ->
                _uiState.update { it.copy(theme = theme) }
            }
        }
    }

    fun setTheme(theme: WWTheme) {
        viewModelScope.launch {
            settingsRepository.setTheme(theme)
        }
    }
}