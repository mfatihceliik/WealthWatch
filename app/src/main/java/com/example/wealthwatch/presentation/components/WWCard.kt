package com.example.wealthwatch.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.wealthwatch.ui.theme.AppTheme
import com.example.wealthwatch.ui.theme.WealthWatchTheme

@Composable
fun WWCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(AppTheme.spacing.containerCornerRadius),
    backgroundColor: Color = AppTheme.colors.card,
    elevation: Dp = AppTheme.spacing.cardElevation, // Flat style for modern look prefered, can be increased
    padding: Dp = AppTheme.spacing.spaceExtraSmall,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val cardColors = CardDefaults.cardColors(containerColor = backgroundColor)
    val cardElevation = CardDefaults.cardElevation(defaultElevation = elevation)
    
    // We wrap the user content in a Column to apply standard padding inside the card
    val internalContent: @Composable ColumnScope.() -> Unit = {
        Column(modifier = Modifier.padding(padding)) {
            content()
        }
    }

    if (onClick != null) {
        Card(
            modifier = modifier,
            shape = shape,
            colors = cardColors,
            elevation = cardElevation,
            onClick = onClick,
            content = internalContent
        )
    } else {
        Card(
            modifier = modifier,
            shape = shape,
            colors = cardColors,
            elevation = cardElevation,
            content = internalContent
        )
    }
}

@Preview
@Composable
private fun WWCardPreview() {
    WealthWatchTheme {
        WWCard(
            modifier = Modifier.padding(8.dp),
            onClick = {}
        ) {
            Text("This is a WealthWatch Card")
            Spacer(modifier = Modifier.height(8.dp))
            Text("It supports content and consistent styling.")
        }
    }
}
