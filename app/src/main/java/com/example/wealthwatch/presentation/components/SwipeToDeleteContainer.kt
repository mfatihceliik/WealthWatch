package com.example.wealthwatch.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wealthwatch.ui.theme.AppTheme
import com.example.wealthwatch.ui.theme.LocalSpacing
import com.example.wealthwatch.ui.theme.WealthWatchTheme

@Composable
fun SwipeToDeleteContainer(
    onDelete: () -> Unit,
    content: @Composable () -> Unit
) {
    val spacing = LocalSpacing.current
    val dismissState = rememberSwipeToDismissBoxState()

    if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
        LaunchedEffect(Unit) {
            onDelete()
            dismissState.snapTo(SwipeToDismissBoxValue.Settled)
        }
    }

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            val color = if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) {
                AppTheme.colors.error
            } else {
                Color.Transparent
            }
            
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = spacing.spaceSmall) // Match item padding if needed, or pass modifier
                    .background(color = color, shape = RoundedCornerShape(spacing.containerCornerRadius))
                    .padding(horizontal = spacing.spaceMedium),
                contentAlignment = Alignment.CenterEnd
            ) {
                WWIcon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Sil",
                    tint = AppTheme.colors.onPrimary // onError usually maps to onPrimary in simple schemes, or custom onError if we had it. Using onPrimary for now as error text is typically light on red.
                )
            }
        },
        content = {
            content()
        }
    )
}

@Preview
@Composable
private fun SwipeToDeleteContainerPreview() {
    WealthWatchTheme {
        SwipeToDeleteContainer(
            onDelete = {}
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(AppTheme.colors.surface),
                contentAlignment = Alignment.CenterStart
            ) {
                WWText("Swipe me left", modifier = Modifier.padding(16.dp))
            }
        }
    }
}
