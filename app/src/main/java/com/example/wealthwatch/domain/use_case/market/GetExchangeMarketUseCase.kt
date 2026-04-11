package com.example.wealthwatch.domain.use_case.market

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.domain.repository.remote.currency.ExchangeRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetExchangeMarketUseCase @Inject constructor(
    private val repository: ExchangeRepository
) {
    operator fun invoke(): Flow<Resource<List<MarketAsset>>> = repository.getExchanges()
}
