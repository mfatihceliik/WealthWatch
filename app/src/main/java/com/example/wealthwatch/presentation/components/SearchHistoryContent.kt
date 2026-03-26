package com.example.wealthwatch.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.wealthwatch.ui.theme.AppTheme
import com.example.wealthwatch.ui.theme.LocalSpacing
import com.example.wealthwatch.ui.theme.WealthWatchTheme

@Composable
fun SearchHistoryContent(
    searchHistory: List<String>,
    onHistoryItemClick: (String) -> Unit,
    onDeleteHistoryItem: (String) -> Unit,
    onClearAllHistory: () -> Unit
) {
    val spacing = LocalSpacing.current

    Column {
        if (searchHistory.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = spacing.spaceSmall),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                WWText(
                    text = "History",
                    style = AppTheme.typography.titleMedium,
                    color = AppTheme.colors.onSurface
                )
                TextButton(
                    onClick = onClearAllHistory
                ) {
                    WWText("Clear All")
                }
            }

            Column {
                searchHistory.forEach { historyItem ->
                    SearchHistoryItem(
                        query = historyItem,
                        onClick = { onHistoryItemClick(historyItem) },
                        onDelete = { onDeleteHistoryItem(historyItem) }
                    )
                }
            }
        }
    }
}

@Composable
fun SearchHistoryItem(
    query: String,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = spacing.spaceSmall, horizontal = spacing.spaceMedium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.List,
            contentDescription = null,
            tint = AppTheme.colors.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(spacing.spaceMedium))
        WWText(
            text = query,
            style = AppTheme.typography.bodyLarge,
            color = AppTheme.colors.onSurface,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onDelete) {
            WWIcon(
                imageVector = Icons.Filled.Close,
                tint = AppTheme.colors.onSurfaceVariant
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
private fun SearchHistoryContentPreview() {
    WealthWatchTheme {
        Surface {
            SearchHistoryContent(
                searchHistory = listOf("Bitcoin", "Apple", "USD/EUR", "Tesla"),
                onHistoryItemClick = {},
                onDeleteHistoryItem = {},
                onClearAllHistory = {}
            )
        }
    }
}
