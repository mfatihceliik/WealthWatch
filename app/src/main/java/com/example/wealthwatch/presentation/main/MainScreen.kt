package com.example.wealthwatch.presentation.main

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wealthwatch.presentation.components.WWTopAppBar
import com.example.wealthwatch.presentation.main.topbar.TopBarViewModel
import com.example.wealthwatch.presentation.navigation.NavigationGraph
import com.example.wealthwatch.presentation.navigation.WealthBottomNavigation
import com.example.wealthwatch.presentation.navigation.rememberNavigationState
import com.example.wealthwatch.ui.theme.AppTheme

@Composable
fun MainScreen(
    topBarViewModel: TopBarViewModel = hiltViewModel(LocalActivity.current as ComponentActivity)
) {
    val navigationState = rememberNavigationState()
    val snackBarHostState = remember { SnackbarHostState() }
    val topBarState by topBarViewModel.state.collectAsStateWithLifecycle()

    val showBottomBar = navigationState.shouldShowBottomBar

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }, topBar = {
            if (topBarState.isTopbarVisible) {
                WWTopAppBar(
                    onNavigateUp = navigationState::navigateUp,
                )
            }
        }, bottomBar = {
            if (showBottomBar) {
                WealthBottomNavigation(
                    currentDestination = navigationState.currentDestination,
                    onNavigate = navigationState::navigateToBottomBarRoute
                )
            }
        },
        containerColor = AppTheme.colors.background
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavigationGraph(navController = navigationState.navController)
        }
    }
}
