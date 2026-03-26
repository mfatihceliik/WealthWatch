package com.example.wealthwatch.presentation.settings.edit_profile

import com.example.wealthwatch.R
import com.example.wealthwatch.presentation.base.BaseViewModel
import com.example.wealthwatch.presentation.main.topbar.TopBarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor() : BaseViewModel() {

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()

    init {
        setTopBarConfig(
            TopBarEvent.SetConfig(
                isTopbarVisible = true,
                title = R.string.settings_edit_profile,
                showBackButton = true
            )
        )
    }
}
