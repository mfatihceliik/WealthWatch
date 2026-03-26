package com.example.wealthwatch.presentation.settings.language

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wealthwatch.presentation.common.BaseScreen
import com.example.wealthwatch.presentation.settings.components.SingleSelectionList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageScreen(
    viewModel: LanguageViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    BaseScreen(
        state = uiState,
        viewModel = viewModel
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            SingleSelectionList(
                options = uiState.availableLanguages,
                selectedOption = uiState.language,
                onOptionSelected = viewModel::setLanguage,
                labelProvider = { stringResource(it.titleResId) })
        }
    }
}
