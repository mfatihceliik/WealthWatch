package com.example.wealthwatch.presentation.settings.security

import com.example.wealthwatch.presentation.base.BaseUiState
import com.example.wealthwatch.presentation.base.ScreenState

data class SecurityUiState(
    override val screenState: ScreenState = ScreenState.HAS_DATA,
    override val message: String = "",
    val isAppLockEnabled: Boolean = false,
    val isBiometricAvailable: Boolean = false,
    val isBiometricEnabled: Boolean = false,
    val isPinSet: Boolean = false,
    val showPinSetup: Boolean = false
) : BaseUiState
