package com.example.wealthwatch.domain.use_case.market

import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.domain.repository.local.settings.SettingsRepository
import com.example.wealthwatch.domain.repository.remote.currency.CurrencyRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

class GetExchangeRatesUseCase @Inject constructor(
    private val repository: CurrencyRepository,
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<Map<String, Double>> {
        val socketFlow = repository.tickerUpdate().map { state ->
            if (state is SocketState.Connected) {
                state.data.associate { currency ->
                    currency.symbol to currency.price
                }
            } else {
                emptyMap()
            }
        }.map { map ->
            if (map.isNotEmpty()) {
                settingsRepository.saveExchangeRates(map)
            }
        }.map { 
            emptyMap<String, Double>()
        }.filter { it.isNotEmpty() }

        return merge(settingsRepository.getExchangeRates(), socketFlow)
    }
}
