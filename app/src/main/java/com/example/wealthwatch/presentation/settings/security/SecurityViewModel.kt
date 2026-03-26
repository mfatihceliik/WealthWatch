package com.example.wealthwatch.presentation.settings.security

import android.app.KeyguardManager
import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.wealthwatch.domain.repository.local.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.example.wealthwatch.presentation.base.BaseViewModel
import com.example.wealthwatch.presentation.main.topbar.TopBarEvent
import com.example.wealthwatch.R

@HiltViewModel
class SecurityViewModel @Inject constructor(
    private val repository: SettingsRepository,
    @ApplicationContext private val context: Context
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(SecurityUiState())
    val uiState: StateFlow<SecurityUiState> = _uiState.asStateFlow()

    init {
        setTopBarConfig(
            TopBarEvent.SetConfig(
                isTopbarVisible = true,
                title = R.string.settings_security,
                showBackButton = true
            )
        )
        checkBiometricAvailability()
        observeSettings()
    }

    private fun observeSettings() {
        launch {
            repository.getAppLockEnabled().collect { enabled ->
                _uiState.update { it.copy(isAppLockEnabled = enabled) }
            }
        }
        launch {
            repository.getBiometricEnabled().collect { enabled ->
                _uiState.update { it.copy(isBiometricEnabled = enabled) }
            }
        }
        launch {
            // Check if PIN is set (hash exists)
            val pinHash = repository.getPinHash()
            _uiState.update { it.copy(isPinSet = !pinHash.isNullOrEmpty()) }
        }
    }

    private fun checkBiometricAvailability() {
        // Using KeyguardManager as a proxy for "Secure Lock Screen" availability
        // Since we don't have androidx.biometric, this is the best standard check for "Device Security"
        val keyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        val isSecure = keyguardManager.isDeviceSecure
        
        _uiState.update { it.copy(isBiometricAvailable = isSecure) }
    }

    fun toggleAppLock(enabled: Boolean) {
        viewModelScope.launch {
            if (enabled) {
                // If enabling, user must set a PIN first if not set (handled in UI)
                // For now just save state
            } else {
                // If disabling, disable everything
                repository.setBiometricEnabled(false)
            }
            repository.setAppLockEnabled(enabled)
        }
    }

    fun toggleBiometric(enabled: Boolean) {
        viewModelScope.launch {
            if (uiState.value.isBiometricAvailable) {
                repository.setBiometricEnabled(enabled)
            }
        }
    }

    fun setPin(pin: String) {
        viewModelScope.launch {
            // Simple hash (SHA-256 ideally) or just store logic
            // For demo/simplicity just string for now, but should hash
            // Let's implement a simple hash?
            // Actually, let's store it directly for now or stick to interface
            // Ideally use MessageDigest
            val hash = java.security.MessageDigest.getInstance("SHA-256")
                .digest(pin.toByteArray())
                .fold("") { str, it -> str + "%02x".format(it) }
            
            repository.setPinHash(hash)
            _uiState.update { it.copy(isPinSet = true) }
        }
    }
}
