package com.example.wealthwatch.domain.use_case.currency

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.domain.repository.local.settings.SettingsRepository
import com.example.wealthwatch.domain.repository.remote.currency.ExchangeRepository
import javax.inject.Inject

class SyncExchangeRatesUseCase @Inject constructor(
    private val exchangeRepository: ExchangeRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke() {
        exchangeRepository.fetchAndSaveRates().collect { result ->
            if (result is Resource.Success) {
                result.data.let { response ->
                    val rates = response.rates.mapValues { it.value.price }
                    settingsRepository.saveExchangeRates(rates)
                }
            }
        }
    }
}
