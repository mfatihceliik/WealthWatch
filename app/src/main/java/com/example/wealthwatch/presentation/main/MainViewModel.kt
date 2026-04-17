package com.example.wealthwatch.presentation.main

import com.example.wealthwatch.domain.repository.local.settings.SettingsRepository
import com.example.wealthwatch.presentation.base.BaseViewModel
import com.example.wealthwatch.presentation.base.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private var hasUnlocked = false

    init {
        observeSettings()
    }

    fun unlock() {
        hasUnlocked = true
        _uiState.update { it.copy(isLocked = false) }
    }

    private fun observeSettings() {
        launch {
            settingsRepository.getAppLockEnabled().collect { isAppLockEnabled ->
                _uiState.update {
                    it.copy(
                        isLocked = isAppLockEnabled && !hasUnlocked,
                        screenState = ScreenState.HAS_DATA
                    )
                }
            }
        }
    }
}
