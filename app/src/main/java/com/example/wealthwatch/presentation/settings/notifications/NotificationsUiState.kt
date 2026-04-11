package com.example.wealthwatch.presentation.settings.notifications

import com.example.wealthwatch.presentation.base.BaseUiState
import com.example.wealthwatch.presentation.base.ScreenState

data class NotificationsUiState(
    override val screenState: ScreenState = ScreenState.HAS_DATA,
    override val message: String = "",
    val priceAlerts: Boolean = false,
    val dailySummary: Boolean = false,
    val productUpdates: Boolean = false
) : BaseUiState
