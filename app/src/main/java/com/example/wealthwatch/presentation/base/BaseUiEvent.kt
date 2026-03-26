package com.example.wealthwatch.presentation.base

sealed interface BaseUiEvent {
    data class ShowSnackBar(val message: String): BaseUiEvent
    data class ShowPopUpDialog(val message: String?, val onRetry: (() -> Unit)? = null): BaseUiEvent
}