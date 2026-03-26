package com.example.wealthwatch.domain.use_case.settings

import com.example.wealthwatch.domain.model.settings.AppCurrency
import com.example.wealthwatch.domain.repository.local.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<AppCurrency> = repository.getCurrency()
}
