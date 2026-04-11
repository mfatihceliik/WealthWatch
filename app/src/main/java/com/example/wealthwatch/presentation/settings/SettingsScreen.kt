package com.example.wealthwatch.presentation.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wealthwatch.R
import com.example.wealthwatch.domain.model.settings.WWTheme
import com.example.wealthwatch.presentation.common.BaseScreen
import com.example.wealthwatch.presentation.components.WWCard
import com.example.wealthwatch.presentation.components.WWIcon
import com.example.wealthwatch.presentation.components.WWText
import com.example.wealthwatch.presentation.navigation.routes.Route
import com.example.wealthwatch.presentation.settings.components.SettingsItem
import com.example.wealthwatch.presentation.settings.components.SettingsProfileHeader
import com.example.wealthwatch.presentation.settings.components.SettingsSectionHeader
import com.example.wealthwatch.ui.theme.AppTheme
import com.example.wealthwatch.ui.theme.WealthWatchTheme

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onNavigate: (Route) -> Unit
) {
    //val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val settingsState by viewModel.settingsState.collectAsStateWithLifecycle()
    val spacing = AppTheme.spacing

    BaseScreen(
        state = settingsState,
        viewModel = viewModel
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Profile Header
            SettingsProfileHeader(
                name = settingsState.userName,
                email = settingsState.userEmail,
                onEditClick = { /* Navigate to Edit Profile */ },
                onLoginClick = { /* Navigate to Login */ })

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            // General Section
            SettingsSectionHeader(text = stringResource(R.string.settings_general))
            Spacer(modifier = Modifier.height(spacing.spaceSmall))

            WWCard(
                modifier = Modifier.fillMaxWidth(),
            ) {
                SettingsItem(
                    icon = Icons.Default.Person,
                    title = stringResource(R.string.settings_edit_profile),
                    onClick = { onNavigate(Route.SettingsEditProfile) /* Navigate to Edit Profile */ })
                HorizontalDivider(color = AppTheme.colors.outline.copy(alpha = 0.1f))

                SettingsItem(
                    icon = Icons.Default.Notifications,
                    title = stringResource(R.string.settings_notifications),
                    onClick = { onNavigate(Route.SettingsNotification)/* Navigate to Notifications */ })
                HorizontalDivider(color = AppTheme.colors.outline.copy(alpha = 0.1f))

                SettingsItem(
                    icon = Icons.Filled.Security, // Using Filled.Security or Lock
                    title = stringResource(R.string.settings_security),
                    onClick = { onNavigate(Route.SettingsSecurity)/* Navigate to Security */ })
            }

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            // Preferences Section
            SettingsSectionHeader(text = stringResource(R.string.settings_preferences))
            Spacer(modifier = Modifier.height(spacing.spaceSmall))

            WWCard(
                modifier = Modifier.fillMaxWidth(), padding = 0.dp
            ) {
                SettingsItem(
                    icon = Icons.Default.Language,
                    title = stringResource(R.string.settings_language),
                    onClick = { onNavigate(Route.SettingsLanguage) })
                HorizontalDivider(color = AppTheme.colors.outline.copy(alpha = 0.1f))

                SettingsItem(
                    icon = Icons.Filled.Nightlight, // Or Nightlight
                    title = stringResource(R.string.settings_dark_mode),
                    isSwitch = true,
                    isChecked = settingsState.theme == WWTheme.DARK,
                    onCheckedChange = { isChecked ->
                        viewModel.setTheme(if (isChecked) WWTheme.DARK else WWTheme.LIGHT)
                    })
            }

            Spacer(modifier = Modifier.height(spacing.spaceLarge))

            // Logout Button
            // Show logout only if user is logged in (optional check, or keep it always)
            if (settingsState.userName != "") {
                Button(
                    onClick = { /* Handle Logout */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(spacing.spaceVeryHuge), // Adjust height specific for logout
                    shape = RoundedCornerShape(spacing.buttonCornerRadius),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colors.card, contentColor = AppTheme.colors.error
                    )
                ) {
                    WWIcon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        modifier = Modifier.size(spacing.iconSmall),
                    )
                    Spacer(modifier = Modifier.width(spacing.spaceSmall))
                    Text(
                        text = stringResource(R.string.settings_logout),
                        style = AppTheme.typography.labelLarge
                    )
                }

                Spacer(modifier = Modifier.height(spacing.spaceLarge))
            }

            // Version Text
            Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                WWText(
                    text = "WealthWatch v${settingsState.appVersion}",
                    style = AppTheme.typography.labelMedium,
                    color = AppTheme.colors.onSurfaceVariant.copy(alpha = 0.5f)
                )
            }

            Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    WealthWatchTheme {
        // Need to mock or provide defaults if ViewModel is required, 
        // ideally separate ScreenContent from Screen with ViewModel.
        // For now, simpler Preview isn't easy with HiltViewModel directly in signature.
        // Skipping full screen preview for now or refactoring. 
        // Let's create a stub since we can't easily mock HiltVM here without abstracting content.
    }
}









