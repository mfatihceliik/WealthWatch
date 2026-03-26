package com.example.wealthwatch.presentation.market

import com.example.wealthwatch.domain.model.settings.AppCurrency

sealed interface MarketUiEvent {
    data class OnCurrencyChange(val currency: AppCurrency) : MarketUiEvent
    object OnRetry : MarketUiEvent
}
