package com.example.wealthwatch.presentation.settings.components

import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wealthwatch.R
import com.example.wealthwatch.presentation.components.WWIcon
import com.example.wealthwatch.presentation.components.WWText
import com.example.wealthwatch.ui.theme.AppTheme
import com.example.wealthwatch.ui.theme.WealthWatchTheme

@Composable
fun SettingsProfileHeader(
    name: String?,
    email: String?,
    onEditClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val spacing = AppTheme.spacing
    val isGuest = name == null

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd
        ) {
            // Avatar Placeholder
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        color = if (isGuest) AppTheme.colors.surfaceVariant.copy(alpha = 0.5f) else AppTheme.colors.surfaceVariant,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                 Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground), // Fallback/Placeholder
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(spacing.spaceExtraSmall)
                        .clip(CircleShape)
                        .background(AppTheme.colors.secondary.copy(alpha = 0.2f)) // Tint if no image
                )
            }

            if (!isGuest) {
                // Edit Icon
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(AppTheme.colors.primary, CircleShape)
                        .clickable { onEditClick() }
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    WWIcon(
                        imageVector = Icons.Default.Edit,
                        tint = AppTheme.colors.onPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        
        if (isGuest) {
            WWText(
                text = stringResource(R.string.guest_user),
                style = AppTheme.typography.headlineSmall,
                color = AppTheme.colors.onBackground
            )
            // Small Login Button (Just text or small button)
             TextButton(onClick = onLoginClick) {
                WWText(
                    text = stringResource(R.string.log_in_or_sign_up),
                    style = AppTheme.typography.labelLarge,
                    color = AppTheme.colors.primary
                )
            }
        } else {
            WWText(
                text = name,
                style = AppTheme.typography.headlineSmall,
                color = AppTheme.colors.onBackground
            )
            
            WWText(
                text = email ?: "",
                style = AppTheme.typography.bodyMedium,
                color = AppTheme.colors.onSurfaceVariant
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SettingsProfileHeaderPreview() {
    WealthWatchTheme {
        SettingsProfileHeader(
            name = "Alex Trader",
            email = "alex@wealthwatch.com",
            onEditClick = {},
            onLoginClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsProfileHeaderGuestPreview() {
    WealthWatchTheme {
        SettingsProfileHeader(
            name = null,
            email = null,
            onEditClick = {},
            onLoginClick = {}
        )
    }
}
