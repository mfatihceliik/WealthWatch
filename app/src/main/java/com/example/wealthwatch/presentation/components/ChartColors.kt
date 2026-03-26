package com.example.wealthwatch.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.example.wealthwatch.ui.theme.AppTheme

@Composable
fun rememberChartColorMapper(): (Int, String) -> Color {
    val defaultColors = listOf(
        AppTheme.colors.primary,
        AppTheme.colors.secondary,
        AppTheme.colors.tertiary,
        AppTheme.colors.error,
        AppTheme.colors.success
    )
    val otherColor = Color(0xFF9E9E9E)
    val emptyColor = Color.LightGray
    val defaultGray = Color.Gray

    return remember(defaultColors) {
        { index, label ->
            when (label) {
                "Other" -> otherColor
                "Empty" -> emptyColor
                else -> defaultColors.getOrElse(index % defaultColors.size) { defaultGray }
            }
        }
    }
}
