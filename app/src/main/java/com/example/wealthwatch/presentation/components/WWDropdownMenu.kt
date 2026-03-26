package com.example.wealthwatch.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.wealthwatch.ui.theme.AppTheme
import com.example.wealthwatch.ui.theme.LocalSpacing

@Composable
fun <T> WWDropdownMenu(
    modifier: Modifier = Modifier,
    items: List<T>,
    selectedItem: T,
    onItemSelect: (T) -> Unit,
    itemLabel: (T) -> String
) {
    var expanded by remember { mutableStateOf(false) }
    val spacing = LocalSpacing.current

    Box(
        modifier = modifier.wrapContentSize(Alignment.TopEnd)
    ) {
        Row(
            modifier = Modifier
                .clickable { expanded = true }
                .padding(spacing.spaceSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            WWText(
                text = itemLabel(selectedItem),
                style = AppTheme.typography.labelLarge,
                color = AppTheme.colors.primary
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = AppTheme.colors.primary
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = AppTheme.colors.card
        ) {
            items.forEach { item ->
                val isSelected = item == selectedItem
                DropdownMenuItem(
                    text = {
                        WWText(
                            text = itemLabel(item),
                            fontWeight = if (isSelected) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Normal
                        )
                    },
                    onClick = {
                        onItemSelect(item)
                        expanded = false
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = if (isSelected) AppTheme.colors.primary else AppTheme.colors.text,
                    )
                )
            }
        }
    }
}
