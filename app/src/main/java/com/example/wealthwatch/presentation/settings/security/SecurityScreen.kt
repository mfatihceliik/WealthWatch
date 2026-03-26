package com.example.wealthwatch.presentation.settings.security

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Dialpad
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wealthwatch.R
import com.example.wealthwatch.presentation.common.BaseScreen
import com.example.wealthwatch.presentation.components.WWCard
import com.example.wealthwatch.presentation.main.topbar.TopBarEvent
import com.example.wealthwatch.presentation.settings.components.SettingsItem
import com.example.wealthwatch.presentation.settings.components.SettingsSectionHeader
import com.example.wealthwatch.presentation.settings.security.components.PinSetupDialog
import com.example.wealthwatch.presentation.settings.security.SecurityViewModel
import com.example.wealthwatch.ui.theme.AppTheme
import com.example.wealthwatch.ui.theme.WealthWatchTheme

@Composable
fun SecurityScreen(
    viewModel: SecurityViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showPinDialog by remember { mutableStateOf(false) }

    BaseScreen(
        state = uiState, // No generic loading state needed for now
        viewModel = viewModel
    ) {
        val spacing = AppTheme.spacing

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            SettingsSectionHeader(text = "App Security")
            Spacer(modifier = Modifier.height(spacing.spaceSmall))

            WWCard(
                modifier = Modifier.fillMaxWidth(),
                padding = 0.dp
            ) {
                // App Lock Toggle
                SettingsItem(
                    title = "App Lock",
                    icon = Icons.Filled.Lock,
                    isSwitch = true,
                    isChecked = uiState.isAppLockEnabled,
                    onCheckedChange = { enabled ->
                        if (enabled && !uiState.isPinSet) {
                            showPinDialog = true
                        } else {
                            viewModel.toggleAppLock(enabled)
                        }
                    }
                )

                // Biometrics Toggle (Only if App Lock is enabled AND device supports it)
                if (uiState.isAppLockEnabled) {
                    HorizontalDivider(color = AppTheme.colors.outline.copy(alpha = 0.1f))

                    SettingsItem(
                        title = "Use Biometrics", // Customize based on face/finger later if possible
                        icon = if (uiState.isBiometricAvailable) Icons.Filled.Face else Icons.Filled.Fingerprint, // Generic icon
                        isSwitch = true,
                        isChecked = uiState.isBiometricEnabled,
                        onCheckedChange = { viewModel.toggleBiometric(it) },
                    )

                    HorizontalDivider(color = AppTheme.colors.outline.copy(alpha = 0.1f))

                    // Change PIN
                    SettingsItem(
                        title = "Change PIN",
                        icon = Icons.Filled.Dialpad,
                        onClick = { showPinDialog = true }
                    )
                }
            }
        }

        if (showPinDialog) {
            PinSetupDialog(
                onDismiss = { showPinDialog = false },
                onPinSet = { pin ->
                    viewModel.setPin(pin)
                    viewModel.toggleAppLock(true) // Enable lock after setting PIN
                    showPinDialog = false
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecurityScreenPreview() {
    WealthWatchTheme {
        // Mock preview logic or extract content
        // SecurityScreen uses VM, so skipping direct preview or need generic one.
    }
}
