package com.example.wealthwatch.presentation.settings.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wealthwatch.presentation.common.BaseScreen
import com.example.wealthwatch.presentation.settings.components.SingleSelectionList

@Composable
fun ThemeScreen(
    viewModel: ThemeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    BaseScreen(
        state = uiState,
        viewModel = viewModel
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SingleSelectionList(
                options = uiState.availableThemes,
                selectedOption = uiState.theme,
                onOptionSelected = viewModel::setTheme,
                labelProvider = { stringResource(it.titleResId) })
        }
    }
}
