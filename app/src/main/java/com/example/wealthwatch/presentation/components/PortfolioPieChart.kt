package com.example.wealthwatch.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.wealthwatch.R
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.presentation.portfolio.PortfolioPieChartData
import com.example.wealthwatch.ui.theme.AppTheme

@Composable
fun PortfolioPieChart(
    modifier: Modifier = Modifier,
    data: List<PortfolioPieChartData>,
    formattedTotalBalance: String, // Added
    isChartGrouped: Boolean,
    onChartModeToggle: () -> Unit,
    onDetailsClick: () -> Unit = {}
) {
    // if (data.isEmpty()) return // Removed to allow empty state

    // Color Setup (omitted common code)
    val colors = listOf(
        Color(0xFF8B5CF6), // Purple
        Color(0xFF10B981), // Green
        Color(0xFFF59E0B), // Amber
        Color(0xFFEF4444), // Red
        Color(0xFF3B82F6), // Blue
        Color(0xFFEC4899), // Pink
        Color(0xFF6366F1), // Indigo
        Color(0xFF84CC16)  // Lime
    )

    val colorMapper: (Int, String) -> Color = { index, _ -> colors[index % colors.size] }

    val pieSlices = remember(data) {
        if (data.isEmpty()) {
            listOf(
                PieSlice(
                    value = 1.0,
                    label = R.string.no_assets.toString(),
                    formattedValue = "",
                    formattedPercentage = "0%",
                    color = Color.LightGray.copy(alpha = 0.3f),
                    category = AssetType.US_STOCK, // Dummy
                    startAngle = -90f,
                    sweepAngle = 360f,
                    titleResId = R.string.no_assets // Or null
                )
            )
        } else {
            calculatePieSlices(data, colorMapper)
        }
    }
    
    var selectedSliceIndex by remember { mutableStateOf<Int?>(null) }

    WWCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { selectedSliceIndex = null }
    ) {
        // 1. Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = AppTheme.spacing.spaceMedium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Side: Toggle + Title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.spaceSmall),
                modifier = Modifier.weight(1f)
            ) {

                WWText(
                    text = stringResource(R.string.asset_allocation),
                    style = AppTheme.typography.titleMedium,
                    color = AppTheme.colors.onSurface,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
            }
            
            // Details Button (fixed width/auto)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { onDetailsClick() }
                    .padding(
                        start = AppTheme.spacing.spaceSmall,
                        top = AppTheme.spacing.spaceExtraSmall,
                        bottom = AppTheme.spacing.spaceExtraSmall
                    ) 
            ) {
                WWText(
                    text = stringResource(R.string.details),
                    style = AppTheme.typography.labelMedium,
                    color = AppTheme.colors.primary
                )
                WWIcon(
                    modifier = Modifier.size(AppTheme.spacing.spaceMedium),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    tint = AppTheme.colors.primary
                )
            }
        }

        // Toggle Button (Icon Only)
        WWIcon(
            modifier = Modifier
                .size(AppTheme.spacing.spaceLarge)
                .clickable { onChartModeToggle() },
            imageVector = if (isChartGrouped) Icons.Default.Category else Icons.Default.PieChart,
            tint = AppTheme.colors.primary
        )

        // 2. Content Row
        // Using intrinsic measurements or weights.
        // The Chart needs space to draw. The Legend needs to scroll.
        // Fixed height for the chart area seems prudent.
        Row(
            modifier = Modifier
                .fillMaxSize(),
                //.height(200.dp), // Give it height
            verticalAlignment = Alignment.CenterVertically
        ) {

            // LEFT: Donut Chart
            // 50% width
            DonutChart(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                selectedSliceIndex = selectedSliceIndex,
                formattedTotalBalance = formattedTotalBalance,
                slices = pieSlices,
                onSliceSelect = { selectedSliceIndex = it },
            )

            // RIGHT: Legend (Grid)
            // 50% width
            ChartLegend(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(start = AppTheme.spacing.spaceMedium),
                slices = if (data.isEmpty()) emptyList() else pieSlices, // Hide legend if empty
                selectedSliceIndex = selectedSliceIndex,
                onSliceClick = { index -> 
                    selectedSliceIndex = if (selectedSliceIndex == index) null else index 
                }
            )
        }
    }
}
