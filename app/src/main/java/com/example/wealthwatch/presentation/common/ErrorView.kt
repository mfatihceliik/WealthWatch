package com.example.wealthwatch.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.wealthwatch.R
import com.example.wealthwatch.presentation.components.WWText
import com.example.wealthwatch.ui.theme.AppTheme
import com.example.wealthwatch.ui.theme.WealthDimensions

@Composable
fun ErrorView(
    spacing: WealthDimensions,
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.padding(spacing.spaceLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = AppTheme.colors.error,
            modifier = Modifier.size(spacing.spaceVeryHuge)
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        WWText(
            text = stringResource(R.string.error_occurred),
            style = AppTheme.typography.titleMedium,
            color = AppTheme.colors.onSurface
        )
        WWText(
            text = message,
            style = AppTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = AppTheme.colors.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        Button(
            onClick = onRetry,
            shape = RoundedCornerShape(spacing.spaceMedium)
        ) {
            WWText(stringResource(R.string.retry))
        }
    }
}