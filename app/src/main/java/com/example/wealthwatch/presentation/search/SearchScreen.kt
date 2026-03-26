package com.example.wealthwatch.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wealthwatch.R
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.presentation.components.MarketListContent
import com.example.wealthwatch.presentation.components.SearchHistoryContent
import com.example.wealthwatch.presentation.components.WWIcon
import com.example.wealthwatch.presentation.components.WWTextField
import com.example.wealthwatch.ui.theme.LocalSpacing

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onNavigateToDetail: (String, AssetType) -> Unit,
    onNavigateUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium)
    ) {

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        WWTextField(
            value = uiState.query,
            onValueChange = { viewModel.onEvent(SearchEvent.OnQueryChanged(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            placeholder = stringResource(R.string.search_hint),
            leadingIcon = {
                IconButton(onClick = onNavigateUp) {
                    WWIcon(imageVector = Icons.AutoMirrored.Filled.ArrowBack)
                }
            },
            trailingIcon = {
                if (uiState.query.isNotEmpty()) {
                    IconButton(onClick = { viewModel.onEvent(SearchEvent.OnClearQuery) }) {
                        WWIcon(imageVector = Icons.Default.Close)
                    }
                }
            },
            shape = RoundedCornerShape(spacing.containerCornerRadius),
            singleLine = true
        )

        Spacer(modifier = Modifier.padding(vertical = LocalSpacing.current.spaceSmall))

        if (uiState.query.isEmpty()) {
            // Show History
            SearchHistoryContent(
                searchHistory = uiState.searchHistory,
                onHistoryItemClick = {
                viewModel.onEvent(SearchEvent.OnHistoryItemClicked(it))
            }, onDeleteHistoryItem = {
                viewModel.onEvent(SearchEvent.OnDeleteHistoryItem(it))
            }, onClearAllHistory = { viewModel.onEvent(SearchEvent.OnClearAllHistory) })
        } else {
            // Show Results
            MarketListContent(
                cryptoList = uiState.cryptoList,
                favorites = uiState.favorites,
                onToggleFavorite = { ticker ->
                    viewModel.onEvent(SearchEvent.OnToggleFavorite(ticker))
                },
                onItemClick = { ticker ->
                    viewModel.saveSearchQuery(uiState.query)
                    onNavigateToDetail(ticker.symbol, ticker.type)
                })
        }
    }
}
