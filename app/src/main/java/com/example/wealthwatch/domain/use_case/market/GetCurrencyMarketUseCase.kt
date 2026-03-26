package com.example.wealthwatch.domain.use_case.market

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.domain.model.currency.Currency
import com.example.wealthwatch.domain.repository.remote.currency.CurrencyRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetCurrencyMarketUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    operator fun invoke(): Flow<Resource<List<Currency>>> = repository.getCurrencies()
}
