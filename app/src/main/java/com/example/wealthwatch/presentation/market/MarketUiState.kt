package com.example.wealthwatch.presentation.market

import androidx.compose.runtime.Immutable
import com.example.wealthwatch.domain.model.settings.AppCurrency
import com.example.wealthwatch.presentation.base.BaseUiState
import com.example.wealthwatch.presentation.base.ScreenState
import com.example.wealthwatch.presentation.model.AssetUiModel

@Immutable
data class MarketUiState(
    override val screenState: ScreenState = ScreenState.LOADING,
    override val message: String = "",
    val marketPulse: List<AssetUiModel> = emptyList(),
    val cryptoGainers: List<AssetUiModel> = emptyList(),
    val cryptoLosers: List<AssetUiModel> = emptyList(),
    val usStockGainers: List<AssetUiModel> = emptyList(),
    val usStockLosers: List<AssetUiModel> = emptyList(),
    val bistGainers: List<AssetUiModel> = emptyList(),
    val bistLosers: List<AssetUiModel> = emptyList(),
    val exchangeGainers: List<AssetUiModel> = emptyList(),
    val exchangeLosers: List<AssetUiModel> = emptyList(),
    val commodityGainers: List<AssetUiModel> = emptyList(),
    val commodityLosers: List<AssetUiModel> = emptyList(),
    val favorites: Set<String> = emptySet(),
    val portfolioBalance: Double = 0.0,
    val currency: AppCurrency = AppCurrency.USD
) : BaseUiState
