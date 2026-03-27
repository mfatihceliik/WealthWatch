package com.example.wealthwatch.domain.use_case.market

import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.domain.repository.local.settings.SettingsRepository
import com.example.wealthwatch.domain.repository.remote.currency.CurrencyRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach

class GetExchangeRatesUseCase @Inject constructor(
    private val repository: CurrencyRepository,
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<Map<String, Double>> {
        val socketFlow = repository.tickerUpdate()
            .map { state ->
                if (state is SocketState.Connected) {
                    state.data.associate { currency ->
                        currency.symbol to currency.currentPrice
                    }
                } else {
                    emptyMap<String, Double>()
                }
            }
            .onEach { map ->
                if (map.isNotEmpty()) {
                    settingsRepository.saveExchangeRates(map)
                }
            }
            .filter { it.isNotEmpty() }

        return merge(settingsRepository.getExchangeRates(), socketFlow)
    }
}
