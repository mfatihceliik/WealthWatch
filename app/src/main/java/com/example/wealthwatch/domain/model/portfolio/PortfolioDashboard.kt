package com.example.wealthwatch.domain.model.portfolio

import com.example.wealthwatch.domain.model.asset.PortfolioAsset
import com.example.wealthwatch.domain.model.settings.AppCurrency

data class PortfolioDashboard(
    val assets: List<PortfolioAsset>,
    val totalBalance: Double,
    val currency: AppCurrency,
    val pieChartData: List<PieChartSegment>
)

data class PieChartSegment(
    val value: Double,
    val label: String,
     // Color logic is UI-specific, but label and value are domain. 
     // We can map colors in ViewModel.
)
