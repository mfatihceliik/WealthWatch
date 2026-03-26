package com.example.wealthwatch.presentation.main

import com.example.wealthwatch.domain.repository.local.settings.SettingsRepository
import com.example.wealthwatch.presentation.base.BaseViewModel
import com.example.wealthwatch.presentation.base.ScreenState
import com.example.wealthwatch.presentation.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : BaseViewModel() {
    private val _uiState: MutableStateFlow<MainUiState> = MutableStateFlow(
        MainUiState(screenState = ScreenState.HAS_DATA)
    )
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    companion object {
        private val TAG = "MainViewModel"
    }

    private var hasUnlocked = false

    fun unlock() {
        hasUnlocked = true
        _uiState.update { it.copy(isLocked = false) }
    }


    init {
        collect()
    }

    fun collect() {
        launch {
            combine(
                settingsRepository.getTheme(),
                settingsRepository.getLanguage(),
                settingsRepository.getAppLockEnabled()
            ) { theme, language, isAppLockEnabled ->
                Triple(theme, language, isAppLockEnabled)
            }.collect { (theme, language, isAppLockEnabled) ->
                _uiState.update {
                    it.copy(
                        theme = theme,
                        language = language,
                        isLocked = isAppLockEnabled && !hasUnlocked,
                        screenState = ScreenState.HAS_DATA
                    )
                }
            }
        }
    }
}
