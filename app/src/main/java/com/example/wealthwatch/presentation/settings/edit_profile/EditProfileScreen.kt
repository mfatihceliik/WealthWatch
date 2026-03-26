package com.example.wealthwatch.presentation.settings.edit_profile

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wealthwatch.R
import com.example.wealthwatch.presentation.common.BaseScreen
import com.example.wealthwatch.presentation.components.WWText
import com.example.wealthwatch.ui.theme.AppTheme
import com.example.wealthwatch.ui.theme.WealthWatchTheme

@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BaseScreen(
        state = uiState,
        viewModel = viewModel
    ) {
        EditProfileContent()
    }
}

@Composable
fun EditProfileContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        WWText(
            text = "Edit Profile Screen (Coming Soon)",
            style = AppTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    WealthWatchTheme {
        EditProfileContent()
    }
}
