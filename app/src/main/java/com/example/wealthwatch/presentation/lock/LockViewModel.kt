package com.example.wealthwatch.presentation.lock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wealthwatch.domain.repository.local.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LockViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    private val _isBiometricAvailable = MutableStateFlow(false)
    val isBiometricAvailable = _isBiometricAvailable.asStateFlow()
    
    // We could store pinHash here once loaded
    private var pinHash: String? = null

    init {
        viewModelScope.launch {
            // Load biometric pref
            launch {
                 val bioEnabled = repository.getBiometricEnabled().first()
                 // Ideally check availability too, but we trust the setting or check Keyguard in Screen
                 _isBiometricAvailable.value = bioEnabled
            }
            // Load PIN hash
            launch {
                pinHash = repository.getPinHash()
            }
        }
    }

    suspend fun validatePin(inputPin: String): Boolean {
        if (pinHash == null) {
            pinHash = repository.getPinHash()
        }
        val inputHash = java.security.MessageDigest.getInstance("SHA-256")
            .digest(inputPin.toByteArray())
            .fold("") { str, it -> str + "%02x".format(it) }
            
        return inputHash == pinHash
    }
}
