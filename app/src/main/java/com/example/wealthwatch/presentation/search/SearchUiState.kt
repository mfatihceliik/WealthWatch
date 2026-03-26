package com.example.wealthwatch.presentation.search

import com.example.wealthwatch.domain.model.settings.AppCurrency
import com.example.wealthwatch.presentation.base.BaseUiState
import com.example.wealthwatch.presentation.base.ScreenState
import com.example.wealthwatch.presentation.model.AssetUiModel

data class SearchUiState(
    override val screenState: ScreenState = ScreenState.LOADING,
    override val message: String = "",
    val query: String = "",
    val cryptoList: List<AssetUiModel> = emptyList(),
    val searchHistory: List<String> = emptyList(),
    val favorites: Set<String> = emptySet(),
    val currency: AppCurrency = AppCurrency.USD,
) : BaseUiState
