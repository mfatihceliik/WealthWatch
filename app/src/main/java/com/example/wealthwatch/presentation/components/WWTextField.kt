package com.example.wealthwatch.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wealthwatch.ui.theme.AppTheme
import com.example.wealthwatch.ui.theme.WealthWatchTheme

@Composable
fun WWTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    shape: Shape? = null,
    isNumeric: Boolean = false,
    isPassword: Boolean = false
) {
    val spacing = AppTheme.spacing
    val finalShape = shape ?: RoundedCornerShape(spacing.inputCornerRadius)

    val effectiveVisualTransformation = if (isPassword) PasswordVisualTransformation() else visualTransformation
    
    val effectiveKeyboardOptions = if (isNumeric) {
        if (isPassword) {
            keyboardOptions.copy(keyboardType = KeyboardType.NumberPassword)
        } else {
            keyboardOptions.copy(keyboardType = KeyboardType.Number)
        }
    } else {
        keyboardOptions
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        label = label?.let { { Text(it) } },
        placeholder = placeholder?.let { { Text(it) } },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        visualTransformation = effectiveVisualTransformation,
        keyboardOptions = effectiveKeyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        shape = finalShape,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = AppTheme.colors.text,
            unfocusedTextColor = AppTheme.colors.onSurfaceVariant,
            focusedBorderColor = AppTheme.colors.primary,
            unfocusedBorderColor = AppTheme.colors.outline,
            focusedLabelColor = AppTheme.colors.primary,
            cursorColor = AppTheme.colors.primary,
            errorBorderColor = AppTheme.colors.error,
            focusedContainerColor = AppTheme.colors.surface,
            unfocusedContainerColor = AppTheme.colors.surface,
        ),
        supportingText = if (isError && errorMessage != null) {
            { Text(text = errorMessage, color = AppTheme.colors.error) }
        } else null)
}

@Preview
@Composable
private fun WWTextFieldPreview() {
    WealthWatchTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            WWTextField(
                value = "Input Text",
                onValueChange = {},
                label = "Label"
            )
            WWTextField(
                value = "",
                onValueChange = {},
                label = "Error Field",
                isError = true,
                errorMessage = "This field is required"
            )
        }
    }
}
