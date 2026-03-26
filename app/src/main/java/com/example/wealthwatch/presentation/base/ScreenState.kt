package com.example.wealthwatch.presentation.base

sealed interface ScreenState {
    data object LOADING : ScreenState
    data object HAS_DATA : ScreenState
    data object NO_DATA : ScreenState
    data object ERROR : ScreenState
}