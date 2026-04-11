package com.example.wealthwatch.domain.model.portfolio

data class PieChartSegment(
    val value: Double,
    val label: String,
    // Color logic is UI-specific, but label and value are domain.
    // We can map colors in ViewModel.
)
