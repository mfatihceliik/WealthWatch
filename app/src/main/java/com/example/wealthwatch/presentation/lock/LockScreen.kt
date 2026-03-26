package com.example.wealthwatch.presentation.lock

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.res.stringResource
import com.example.wealthwatch.R
import com.example.wealthwatch.presentation.components.WWIcon
import com.example.wealthwatch.presentation.components.WWText
import com.example.wealthwatch.ui.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
fun LockScreen(
    onUnlock: () -> Unit,
    viewModel: LockViewModel = hiltViewModel()
) {
    val isBiometricEnabled by viewModel.isBiometricAvailable.collectAsState()
    var pinInput by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    val spacing = AppTheme.spacing

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
            .padding(spacing.spaceLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // App Icon or Lock Icon
        WWIcon(
            imageVector = Icons.Filled.Lock,
            tint = AppTheme.colors.primary,
            modifier = Modifier
                .size(80.dp)
                .background(AppTheme.colors.primary.copy(alpha = 0.1f), CircleShape)
                .padding(20.dp)
        )
        
        Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
        
        WWText(
            text = stringResource(R.string.app_name),
            style = AppTheme.typography.titleLarge,
            color = AppTheme.colors.onSurface
        )

        Spacer(modifier = Modifier.height(spacing.spaceSmall))

        WWText(
            text = if (error != null) error!! else "Enter PIN to Unlock",
            style = AppTheme.typography.bodyMedium,
            color = if (error != null) AppTheme.colors.error else AppTheme.colors.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        
        // PIN Dots
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().height(24.dp)
        ) {
            repeat(4) { index ->
                val isFilled = index < pinInput.length
                val color = if (isFilled) AppTheme.colors.primary else AppTheme.colors.outline.copy(alpha = 0.5f)
                val size = if (isFilled) 16.dp else 12.dp
                
                Box(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .size(size)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
        
        // Number Pad
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            val rows = listOf(
                listOf("1", "2", "3"),
                listOf("4", "5", "6"),
                listOf("7", "8", "9"),
                listOf("Bio", "0", "Del")
            )
            
            rows.forEach { row ->
                Row(
                   horizontalArrangement = Arrangement.SpaceEvenly,
                   modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
                ) {
                    row.forEach { key ->
                        // Key Item
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .clickable(
                                    enabled = key != "Bio" || isBiometricEnabled,
                                    onClick = {
                                        when (key) {
                                            "Del" -> { 
                                                if (pinInput.isNotEmpty()) pinInput = pinInput.dropLast(1)
                                                error = null
                                            }
                                            "Bio" -> {
                                                // Trigger biometric
                                            }
                                            else -> {
                                                // Standard Number
                                                if (pinInput.length < 4) {
                                                    pinInput += key
                                                    if (pinInput.length == 4) {
                                                        scope.launch {
                                                            if (viewModel.validatePin(pinInput)) {
                                                                onUnlock()
                                                            } else {
                                                                error = "Incorrect PIN"
                                                                // Haptic feedback could go here
                                                                pinInput = ""
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            when (key) {
                                "Bio" -> {
                                    if (isBiometricEnabled) {
                                        WWIcon(
                                            imageVector = Icons.Filled.Face, 
                                            tint = AppTheme.colors.primary,
                                            modifier = Modifier.size(28.dp)
                                        )
                                    }
                                }
                                "Del" -> {
                                    WWIcon(
                                        imageVector = Icons.AutoMirrored.Filled.Backspace,
                                        tint = AppTheme.colors.onSurface,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                                else -> {
                                    WWText(
                                        text = key,
                                        style = AppTheme.typography.headlineMedium,
                                        color = AppTheme.colors.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
