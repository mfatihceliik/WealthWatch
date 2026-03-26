package com.example.wealthwatch.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.wealthwatch.R
import com.example.wealthwatch.presentation.components.WWText
import com.example.wealthwatch.ui.theme.AppTheme
import com.example.wealthwatch.ui.theme.WealthDimensions

@Composable
fun LoadingView(
    spacing: WealthDimensions
) {
    Column(
        modifier = Modifier.fillMaxSize().background(AppTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            color = AppTheme.colors.primary,
            strokeWidth = 4.dp
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        WWText(
            text = stringResource(R.string.loading),
            style = AppTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}