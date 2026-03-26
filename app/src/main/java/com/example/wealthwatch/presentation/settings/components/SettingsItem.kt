package com.example.wealthwatch.presentation.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import com.example.wealthwatch.presentation.components.WWIcon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.wealthwatch.presentation.components.WWText
import com.example.wealthwatch.ui.theme.AppTheme
import com.example.wealthwatch.ui.theme.WealthWatchTheme

@Composable
fun SettingsItem(
    title: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    trailingText: String? = null,
    isSwitch: Boolean = false,
    isChecked: Boolean = false,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    val spacing = AppTheme.spacing
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null && !isSwitch) { onClick?.invoke() }
            .padding(spacing.spaceMedium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            WWIcon(
                imageVector = icon,
                tint = AppTheme.colors.text,
                modifier = Modifier.size(spacing.iconMedium)
            )
            Spacer(modifier = Modifier.width(spacing.spaceMedium))
        }
        
        WWText(
            text = title,
            style = AppTheme.typography.bodyLarge,
            color = AppTheme.colors.onSurface,
            modifier = Modifier.weight(1f)
        )
        
        if (trailingContent != null) {
            trailingContent()
        } else {
            when {
                isSwitch -> {
                     Switch(
                         checked = isChecked,
                         onCheckedChange = onCheckedChange,
                         colors = SwitchDefaults.colors(
                             checkedThumbColor = AppTheme.colors.primary,
                             checkedTrackColor = AppTheme.colors.primary.copy(alpha = 0.5f),
                             uncheckedThumbColor = AppTheme.colors.outline,
                             uncheckedTrackColor = AppTheme.colors.surfaceVariant
                         )
                     )
                }
                trailingText != null -> {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        WWText(
                            text = trailingText,
                            style = AppTheme.typography.bodyMedium,
                            color = AppTheme.colors.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceSmall))
                        WWIcon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            tint = AppTheme.colors.text,
                            modifier = Modifier.size(spacing.iconSmall)
                        )
                    }
                }
                else -> {
                    WWIcon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        tint = AppTheme.colors.text,
                        modifier = Modifier.size(spacing.iconSmall)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SettingsItemPreview() {
    WealthWatchTheme {
        SettingsItem(
            icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            title = "Sample Setting",
            trailingText = "Value",
            onClick = {}
        )
    }
}
