package com.example.wealthwatch.presentation.common

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wealthwatch.presentation.base.BaseViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wealthwatch.presentation.base.BaseUiState
import com.example.wealthwatch.presentation.base.ScreenState
import com.example.wealthwatch.presentation.components.WWText
import com.example.wealthwatch.presentation.main.topbar.TopBarViewModel
import com.example.wealthwatch.ui.theme.AppTheme

@Composable
fun <State : BaseUiState> BaseScreen(
    modifier: Modifier = Modifier,
    state: State,
    onRetry: () -> Unit = {},
    topBarViewModel: TopBarViewModel = hiltViewModel(LocalActivity.current as ComponentActivity),
    viewModel: BaseViewModel,
    content: @Composable () -> Unit
) {
    val spacing = AppTheme.spacing

    val vmConfigState = viewModel.topBarConfig.collectAsStateWithLifecycle()
    val vmConfig = vmConfigState.value

    LaunchedEffect(vmConfig) {
        topBarViewModel.onEvent(vmConfig)
    }


    Box(
        modifier = modifier.fillMaxSize()
            .padding(spacing.spaceSmall),
        contentAlignment = Alignment.Center
    ) {
        when (state.screenState) {
            ScreenState.LOADING -> LoadingView(spacing)
            ScreenState.NO_DATA -> WWText("No Data", modifier = Modifier.align(Alignment.Center))
            ScreenState.HAS_DATA -> content()
            ScreenState.ERROR -> ErrorView(spacing, state.message, onRetry)
        }
    }
}
