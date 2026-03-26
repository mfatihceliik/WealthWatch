package com.example.wealthwatch.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// 1. Define your custom color schema
@Immutable
data class AppColors(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val outline: Color,
    val tertiary: Color,

    val success: Color,
    val error: Color,
    val icon: Color,
    val card: Color,
    val button: Color,
    val text: Color,

    val priceUp: Color,
    val priceDown: Color,
    val priceNeutral: Color,

)

// 2. Define Light and Dark palettes using your names
val LightAppColors = AppColors(
    primary = Primary50Base,
    onPrimary = Black80Base,

    secondary = Secondary50Base,
    onSecondary = White100,

    background = White100,
    onBackground = Black80Base,

    surface = Black20,
    onSurface = Black60,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = Gray70,
    outline = Gray30,
    tertiary = CryptoBlue,

    success = Success50Base,
    error = Danger50Base,

    icon = Primary50Base,
    card = Black20,
    button = Primary50Base,
    text = Black80Base,

    priceUp = Success60,
    priceDown = Danger60,
    priceNeutral = Black40
)

val DarkAppColors = AppColors(
    primary = Primary60,
    onPrimary = Black80Base,

    secondary = Secondary60,
    onSecondary = Black80Base,

    background = Black80Base,
    onBackground = White100,

    surface = Black60,
    onSurface = White100,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = Gray50Base,
    outline = Black40,
    tertiary = CryptoBlue,

    success = Success60,
    error = Danger60,

    icon = Primary50Base,
    card = Black60,
    button = Primary60,
    text = White100,

    priceUp = Success40,
    priceDown = Danger40,
    priceNeutral = Black40
)


// 3. Create CompositionLocal
val LocalAppColors = staticCompositionLocalOf { LightAppColors }

// 4. Create easy accessor object
object AppTheme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val spacing: WealthDimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalSpacing.current

    val typography: androidx.compose.material3.Typography
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.typography
}

@Composable
fun WealthWatchTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // We removed dynamicColor support for now to enforce OUR branding,
    // but if user really wants it we can add logic back to map basic slots.
    content: @Composable () -> Unit
) {
    val appColors = if (darkTheme) DarkAppColors else LightAppColors

    CompositionLocalProvider(
        LocalAppColors provides appColors,
        LocalSpacing provides WealthDimensions()
    ) {
        MaterialTheme(
            typography = Typography,
            content = content
        )
    }
}
