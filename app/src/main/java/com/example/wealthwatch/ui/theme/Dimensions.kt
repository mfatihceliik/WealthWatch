package com.example.wealthwatch.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class WealthDimensions(
    val default: Dp = 0.dp,
    val spaceTiny: Dp = 2.dp,
    val spaceExtraSmall: Dp = 4.dp,
    val spaceSmall: Dp = 8.dp,
    val spaceMedium: Dp = 16.dp,
    val spaceLarge: Dp = 24.dp,
    val spaceExtraLarge: Dp = 32.dp,
    val spaceHuge: Dp = 48.dp,
    val spaceVeryHuge: Dp = 64.dp,
/**//**//**//**//**//**//**//**/
    val cardElevation: Dp = 4.dp,
    val containerCornerRadius: Dp = 16.dp,
    val buttonCornerRadius: Dp = 12.dp,
    val inputCornerRadius: Dp = 12.dp,
    val buttonHeight: Dp = 56.dp,
    val backIcon: Dp = 48.dp,
    val defaultIcon: Dp = 32.dp,
    val iconSmall: Dp = 24.dp,
    val iconMedium: Dp = 32.dp
)

val LocalSpacing = staticCompositionLocalOf { WealthDimensions() }