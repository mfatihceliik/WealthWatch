package com.example.wealthwatch.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.wealthwatch.ui.theme.AppTheme

@Composable
fun ChartLegend(
    slices: List<PieSlice>,
    selectedSliceIndex: Int?,
    onSliceClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyGridState()
    
    LaunchedEffect(selectedSliceIndex) {
        if (selectedSliceIndex != null) {
            listState.animateScrollToItem(selectedSliceIndex)
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxHeight(),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.spaceSmall), // Vertical gap between rows
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.spaceSmall) // Horizontal gap between columns
    ) {
        itemsIndexed(slices) { index, slice ->
            val isSelected = selectedSliceIndex == index
            val alpha = if (selectedSliceIndex == null || isSelected) 1f else 0.3f

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSliceClick(index) }
                    .padding(vertical = AppTheme.spacing.spaceExtraSmall)
            ) {
                // Color Dot
                Box(
                    modifier = Modifier
                        .size(AppTheme.spacing.spaceSmall)
                        .clip(CircleShape)
                        .background(slice.color.copy(alpha = alpha))
                )

                Spacer(modifier = Modifier.width(AppTheme.spacing.spaceSmall))

                Column {
                    WWText(
                        text = if (slice.titleResId != null) stringResource(slice.titleResId) else slice.label,
                        style = AppTheme.typography.labelSmall,
                        color = AppTheme.colors.onSurface.copy(alpha = alpha),
                        maxLines = 1
                    )
                    WWText(
                        text = slice.formattedPercentage,
                        style = AppTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp // Compact font
                        ),
                        color = AppTheme.colors.onSurface.copy(alpha = alpha)
                    )
                }
            }
        }
    }
}
