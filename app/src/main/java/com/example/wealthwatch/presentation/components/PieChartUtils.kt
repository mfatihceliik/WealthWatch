package com.example.wealthwatch.presentation.components

import androidx.compose.ui.graphics.Color
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.presentation.portfolio.PortfolioPieChartData

data class PieSlice(
    val value: Double,
    val label: String,
    val formattedValue: String, // Added
    val formattedPercentage: String,
    val color: Color,
    val category: AssetType,
    val startAngle: Float,
    val sweepAngle: Float,
    val titleResId: Int? = null
)

fun calculatePieSlices(
    data: List<PortfolioPieChartData>,
    colorMapper: (Int, String) -> Color
): List<PieSlice> {
    val total = data.sumOf { it.value }
    var currentStartAngle = -90f

    return data.mapIndexed { index, item ->
        val color = colorMapper(index, item.label)
        val sweepAngle = if (total > 0) (360f * (item.value / total)).toFloat() else 0f
        
        val slice = PieSlice(
            value = item.value,
            label = item.label,
            formattedValue = item.formattedValue, // Pass it through
            formattedPercentage = item.formattedPercentage,
            color = color,
            category = item.category,
            startAngle = currentStartAngle,
            sweepAngle = sweepAngle,
            titleResId = item.titleResId
        )

        currentStartAngle += sweepAngle
        slice
    }
}

