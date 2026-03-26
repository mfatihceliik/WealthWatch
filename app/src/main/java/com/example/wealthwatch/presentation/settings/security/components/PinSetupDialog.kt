package com.example.wealthwatch.presentation.settings.security.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.wealthwatch.presentation.components.WWText
import com.example.wealthwatch.presentation.components.WWTextField
import com.example.wealthwatch.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinSetupDialog(
    onDismiss: () -> Unit,
    onPinSet: (String) -> Unit
) {
    var pin by remember { mutableStateOf("") }
    var confirmPin by remember { mutableStateOf("") }
    var isConfirming by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    
    val spacing = AppTheme.spacing

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(spacing.containerCornerRadius))
                .background(AppTheme.colors.surface)
                .padding(spacing.spaceLarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WWText(
                text = if (isConfirming) "Confirm PIN" else "Set PIN",
                style = AppTheme.typography.headlineSmall,
                color = AppTheme.colors.onSurface
            )
            
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            
            WWText(
                text = if (isConfirming) "Re-enter your 4-digit PIN" else "Enter a 4-digit PIN to secure your app",
                style = AppTheme.typography.bodyMedium,
                color = AppTheme.colors.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            
            // Password dots visualizer could go here, for now simple TextField for speed
            // Or better, a custom OTP-like input using BasicTextField if needed, but standard text field is easiest for now.
            // Using WWTextField with number input.
            
            WWTextField(
                value = if (isConfirming) confirmPin else pin,
                onValueChange = { 
                    if (it.length <= 4 && it.all { char -> char.isDigit() }) {
                        if (isConfirming) confirmPin = it else pin = it
                        error = null
                    }
                },
                placeholder = "____",
                isNumeric = true,
                isPassword = true,
                modifier = Modifier.width(150.dp)
            )
            
            if (error != null) {
                Spacer(modifier = Modifier.height(spacing.spaceSmall))
                WWText(
                    text = error!!,
                    style = AppTheme.typography.labelMedium,
                    color = AppTheme.colors.error
                )
            }
            
            Spacer(modifier = Modifier.height(spacing.spaceLarge))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onDismiss) {
                    WWText(text = "Cancel", style = AppTheme.typography.labelLarge, color = AppTheme.colors.primary)
                }
                
                Spacer(modifier = Modifier.width(spacing.spaceMedium))
                
                TextButton(
                    onClick = {
                        val currentInput = if (isConfirming) confirmPin else pin
                        if (currentInput.length != 4) {
                            error = "PIN must be 4 digits"
                            return@TextButton
                        }
                        
                        if (isConfirming) {
                            if (confirmPin == pin) {
                                onPinSet(pin)
                            } else {
                                error = "PINs do not match"
                                confirmPin = "" // Reset confirm
                            }
                        } else {
                            isConfirming = true
                        }
                    }
                ) {
                    WWText(
                        text = if (isConfirming) "Confirm" else "Next", 
                        style = AppTheme.typography.labelLarge, 
                        color = AppTheme.colors.primary
                    )
                }
            }
        }
    }
}
