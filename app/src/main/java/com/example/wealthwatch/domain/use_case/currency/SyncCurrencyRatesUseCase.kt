package com.example.wealthwatch.domain.use_case.currency

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.domain.repository.local.settings.SettingsRepository
import com.example.wealthwatch.domain.repository.remote.currency.CurrencyRepository
import javax.inject.Inject

class SyncCurrencyRatesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke() {
        currencyRepository.fetchAndSaveRates().collect { result ->
            if (result is Resource.Success) {
                result.data.let { response ->
                    val rates = response.rates.mapValues { it.value.price }
                    settingsRepository.saveExchangeRates(rates)
                }
            }
        }
    }
}
