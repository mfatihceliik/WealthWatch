package com.example.wealthwatch.presentation.portfolio

import com.example.wealthwatch.domain.model.asset.AssetType

data class PortfolioPieChartData(
    val value: Double,
    val label: String,
    val category: AssetType,
    val titleResId: Int = -1,
    val formattedPercentage: String = "",
    val formattedValue: String = ""
)
