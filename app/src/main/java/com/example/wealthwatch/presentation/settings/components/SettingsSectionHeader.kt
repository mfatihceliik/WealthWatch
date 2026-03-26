package com.example.wealthwatch.presentation.settings.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.wealthwatch.presentation.components.WWText
import com.example.wealthwatch.ui.theme.AppTheme
import com.example.wealthwatch.ui.theme.WealthWatchTheme

@Composable
fun SettingsSectionHeader(text: String) {
    WWText(
        text = text.uppercase(),
        style = AppTheme.typography.labelMedium,
        color = AppTheme.colors.onSurfaceVariant,
        modifier = Modifier.padding(start = AppTheme.spacing.spaceSmall)
    )
}

@Preview(showBackground = true)
@Composable
fun SettingsSectionHeaderPreview() {
    WealthWatchTheme {
        SettingsSectionHeader(text = "General Settings")
    }
}
