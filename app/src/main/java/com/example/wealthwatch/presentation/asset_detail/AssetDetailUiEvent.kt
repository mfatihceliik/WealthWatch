package com.example.wealthwatch.presentation.asset_detail

import com.example.wealthwatch.presentation.base.BaseUiEvent

sealed class AssetDetailUiEvent {
    object OnToggleFavorite : AssetDetailUiEvent()
    object OnNavigateBack : AssetDetailUiEvent()
    data class OnChangeChartPeriod(val period: String) : AssetDetailUiEvent()
    data class OnAmountChange(val amount: String) : AssetDetailUiEvent()
    data class OnPriceChange(val price: String) : AssetDetailUiEvent()
    data class OnNoteChange(val note: String) : AssetDetailUiEvent()
    data class OnToggleMode(val isBuy: Boolean) : AssetDetailUiEvent()
    object OnBuyClick : AssetDetailUiEvent()
    object OnSellClick : AssetDetailUiEvent()
    object TransactionSuccess : AssetDetailUiEvent()
    data class OnDeleteTransaction(val transaction: com.example.wealthwatch.domain.model.asset.Transaction) : AssetDetailUiEvent()
}
