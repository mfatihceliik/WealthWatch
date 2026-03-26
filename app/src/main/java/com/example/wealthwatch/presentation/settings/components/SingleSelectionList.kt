package com.example.wealthwatch.presentation.settings.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wealthwatch.presentation.components.WWCard
import com.example.wealthwatch.ui.theme.AppTheme
import androidx.compose.foundation.layout.fillMaxWidth
import com.example.wealthwatch.presentation.components.WWIcon

@Composable
fun <T> SingleSelectionList(
    options: List<T>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit,
    labelProvider: @Composable (T) -> String
) {
    WWCard(
        modifier = Modifier.fillMaxWidth(),
        padding = 0.dp
    ) {
        options.forEachIndexed { index, option ->
            val isSelected = option == selectedOption
            
            SettingsItem(
                title = labelProvider(option),
                onClick = { onOptionSelected(option) },
                trailingContent = if (isSelected) {
                    {
                        WWIcon(
                            imageVector = Icons.Filled.Check,
                            tint = AppTheme.colors.primary,
                            modifier = Modifier.size(AppTheme.spacing.iconMedium)
                        )
                    }
                } else null
            )
            
            if (index < options.lastIndex) {
                 HorizontalDivider(
                    color = AppTheme.colors.outline.copy(alpha = 0.1f)
                )
            }
        }
    }
}
