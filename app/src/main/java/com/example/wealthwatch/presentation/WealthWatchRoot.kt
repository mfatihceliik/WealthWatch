package com.example.wealthwatch.presentation

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.example.wealthwatch.domain.model.settings.WWTheme
import com.example.wealthwatch.presentation.base.ScreenState
import com.example.wealthwatch.presentation.common.LoadingView
import com.example.wealthwatch.presentation.lock.LockScreen
import com.example.wealthwatch.presentation.main.MainScreen
import com.example.wealthwatch.presentation.main.MainUiState
import com.example.wealthwatch.ui.theme.LocalSpacing
import com.example.wealthwatch.ui.theme.WealthWatchTheme

@Composable
fun WealthWatchRoot(
    appState: MainUiState,
    onUnlock: () -> Unit
) {
    val darkTheme = when (appState.theme) {
        WWTheme.LIGHT -> false
        WWTheme.DARK -> true
        WWTheme.SYSTEM -> {
            // Check if we pre-forced Night Mode in App class (legacy native check)
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                true
            } else {
                isSystemInDarkTheme()
            }
        }
    }

    WealthWatchTheme(darkTheme = darkTheme) {
        when {
            appState.screenState == ScreenState.LOADING -> {
                LoadingView(LocalSpacing.current)
            }
            appState.isLocked -> {
                LockScreen(onUnlock = onUnlock)
            }
            else -> {
                MainScreen()
            }
        }
    }
}