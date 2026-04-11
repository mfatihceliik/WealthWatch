package com.example.wealthwatch.presentation.settings.edit_profile

import com.example.wealthwatch.presentation.base.BaseUiState
import com.example.wealthwatch.presentation.base.ScreenState

data class EditProfileUiState(
    override val screenState: ScreenState = ScreenState.HAS_DATA,
    override val message: String = "",
    val name: String = "",
    val email: String = "",
    val isSaving: Boolean = false
) : BaseUiState
