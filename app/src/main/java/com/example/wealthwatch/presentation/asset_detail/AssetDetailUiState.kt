package com.example.wealthwatch.presentation.asset_detail

import com.example.wealthwatch.presentation.base.BaseUiState
import com.example.wealthwatch.presentation.base.ScreenState
import com.example.wealthwatch.presentation.model.AssetUiModel
import com.example.wealthwatch.domain.model.asset.Transaction

data class AssetDetailUiState(
    override val screenState: ScreenState = ScreenState.LOADING,
    override val message: String = "",
    val asset: AssetUiModel? = null,
    val isFavorite: Boolean = false,
    val chartData: List<Float> = emptyList(),
    val selectedPeriod: String = "1D",
    val transactions: List<Transaction> = emptyList(),
    val amountInput: String = "",
    val priceInput: String = "",
    val noteInput: String = "",
    val isBuyMode: Boolean = true,
    val currency: com.example.wealthwatch.domain.model.settings.AppCurrency = com.example.wealthwatch.domain.model.settings.AppCurrency.USD,
    val exchangeRate: Double = 1.0
) : BaseUiState
