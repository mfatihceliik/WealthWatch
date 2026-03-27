package com.example.wealthwatch.domain.use_case.market

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.di.DispatcherDefault
import com.example.wealthwatch.domain.model.market.Market
import com.example.wealthwatch.domain.repository.remote.market.MarketRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMarketOverviewUseCase @Inject constructor(
    private val repository: MarketRepository,
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher
) {
    operator fun invoke(): Flow<Resource<Market>> = repository.getMarketOverview().flowOn(defaultDispatcher)
}
