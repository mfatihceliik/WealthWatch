package com.example.wealthwatch.presentation.portfolio

import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.domain.model.settings.AppCurrency
import com.example.wealthwatch.presentation.base.BaseUiState
import com.example.wealthwatch.presentation.base.ScreenState
import com.example.wealthwatch.presentation.model.AssetUiModel

data class PortfolioUiState(
    override val screenState: ScreenState = ScreenState.LOADING,
    override val message: String = "",
    val totalBalance: Double = 0.0,
    val formattedTotalBalance: String = "",
    val currency: AppCurrency = AppCurrency.USD,
    val exchangeRate: Double = 1.0,
    val assets: List<AssetUiModel> = emptyList(),
    val sections: List<PortfolioAssetSection> = emptyList(),
    val isPieChartGrouped: Boolean = true,
    val pieChartData: List<PortfolioPieChartData> = emptyList(),
    val expandedCategories: Set<AssetType> = emptySet()
) : BaseUiState