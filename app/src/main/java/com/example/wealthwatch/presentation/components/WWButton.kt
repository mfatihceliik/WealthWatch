package com.example.wealthwatch.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.wealthwatch.ui.theme.AppTheme
import com.example.wealthwatch.ui.theme.LocalSpacing
import com.example.wealthwatch.ui.theme.WealthWatchTheme

@Composable
fun WWButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    val spacing = LocalSpacing.current
    val colors = AppTheme.colors

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(spacing.buttonHeight),
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(spacing.buttonCornerRadius),
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.primary,
            contentColor = colors.text,
            disabledContainerColor = colors.primary.copy(alpha = 0.5f),
            disabledContentColor = colors.text.copy(alpha = 0.5f)
        ),
        contentPadding = PaddingValues(vertical = spacing.spaceSmall)
    ) {
        if (isLoading) {
            // Şimdilik loading text, ileride CircularProgressIndicator eklenebilir
            Text(
                text = "Loading...",
            )
        } else {
            Text(
                text = text,
            )
        }
    }
}

@Preview
@Composable
fun WealthWatchButtonPreview() {
    WealthWatchTheme {
        WWButton(
            text = "Primary Button",
            onClick = {},
        )
    }
}
