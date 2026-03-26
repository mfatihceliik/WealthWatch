package com.example.wealthwatch.presentation.components

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wealthwatch.R
import com.example.wealthwatch.presentation.main.topbar.TopBarEvent
import com.example.wealthwatch.presentation.main.topbar.TopBarViewModel
import com.example.wealthwatch.ui.theme.AppTheme
import com.example.wealthwatch.ui.theme.WealthWatchTheme

@Composable
fun WWTopAppBar(
    topBarViewModel: TopBarViewModel = hiltViewModel(LocalActivity.current as ComponentActivity),
    onNavigateUp: () -> Unit,
) {
    val uiState by topBarViewModel.state.collectAsStateWithLifecycle()
    
    WWTopAppBarContent(
        title = uiState.titleString ?: uiState.titleResId?.let { stringResource(it) } ?: "",
        showBackButton = uiState.showBackButton,
        onNavigateUp = onNavigateUp,
        isSearchVisible = uiState.isSearchVisible,
        query = uiState.query,
        onQueryChange = { topBarViewModel.onEvent(TopBarEvent.OnQueryChanged(it)) },
        onSearchFocusChange = { topBarViewModel.onEvent(TopBarEvent.OnSearchFocusChanged(it)) }
    )
}
@Composable
fun WWTopAppBarContent(
    modifier: Modifier = Modifier,
    title: String,
    showBackButton: Boolean,
    onNavigateUp: () -> Unit,
    isSearchVisible: Boolean = false,
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    onSearchFocusChange: (Boolean) -> Unit = {}
) {
    val spacing = AppTheme.spacing
    Row( modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background)
            .statusBarsPadding()
            .padding(horizontal = spacing.spaceMedium, vertical = spacing.spaceSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showBackButton) {
            IconButton(
                onClick = onNavigateUp,
                modifier = Modifier.size(spacing.backIcon)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = AppTheme.colors.onBackground
                )
            }
            Spacer(modifier = Modifier.width(spacing.spaceSmall))
        }

        if (isSearchVisible) {
            WWTextField(
                value = query,
                onValueChange = onQueryChange,
                placeholder = stringResource(R.string.search_hint),
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged { onSearchFocusChange(it.isFocused) },
                trailingIcon = if (query.isNotEmpty()) {
                    {
                        IconButton(onClick = { onQueryChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear",
                                tint = AppTheme.colors.onSurfaceVariant
                            )
                        }
                    }
                } else null
            )
        } else {
            WWText(
                text = title,
                style = AppTheme.typography.titleMedium,
                color = AppTheme.colors.onBackground
            )
        }
    }
}

@Preview
@Composable
private fun WWTopAppBarPreview() {
    WealthWatchTheme {
        WWTopAppBarContent(
            title = "WealthWatch",
            showBackButton = true,
            onNavigateUp = {}
        )
    }
}
