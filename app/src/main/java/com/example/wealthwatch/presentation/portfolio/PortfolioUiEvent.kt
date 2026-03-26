package com.example.wealthwatch.presentation.portfolio

import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.domain.model.settings.AppCurrency
import com.example.wealthwatch.presentation.base.BaseUiEvent

sealed interface PortfolioUiEvent {
    object OnToggleChartMode : PortfolioUiEvent
    data class OnCategoryClick(val category: AssetType) : PortfolioUiEvent
    data class OnCurrencyChange(val currency: AppCurrency) : PortfolioUiEvent
    data class OnAssetClick(val symbol: String, val type: AssetType) : PortfolioUiEvent
}
