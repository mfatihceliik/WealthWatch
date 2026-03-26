package com.example.wealthwatch.presentation.watchlist

import com.example.wealthwatch.domain.model.settings.AppCurrency
import com.example.wealthwatch.presentation.base.BaseUiState
import com.example.wealthwatch.presentation.base.ScreenState
import com.example.wealthwatch.presentation.model.AssetUiModel

data class WatchlistState(
    override val screenState: ScreenState = ScreenState.LOADING,
    override val message: String = "",
    val cryptoList: List<AssetUiModel> = emptyList(),
    val currency: AppCurrency = AppCurrency.USD,
) : BaseUiState
