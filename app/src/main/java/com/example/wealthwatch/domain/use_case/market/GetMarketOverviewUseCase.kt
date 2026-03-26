package com.example.wealthwatch.domain.use_case.market

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.data.mapper.MarketMapper
import com.example.wealthwatch.di.DispatcherDefault
import com.example.wealthwatch.domain.model.market.Market
import com.example.wealthwatch.domain.repository.remote.market.MarketRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMarketOverviewUseCase @Inject constructor(
    private val repository: MarketRepository,
    private val marketMapper: MarketMapper,
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher
) {
    operator fun invoke(): Flow<Resource<Market>> = repository.getMarketOverview().map { resource ->
        when (resource) {
            Resource.Loading -> Resource.Loading
            is Resource.Success -> {
                try {
                    val market = Market(
                        pulse = marketMapper.map(resource.data.pulse),
                        crypto = marketMapper.map(resource.data.crypto),
                        usStock = marketMapper.map(resource.data.usStock),
                        trStock = marketMapper.map(resource.data.trStock),
                        currency = marketMapper.map(resource.data.currency),
                        commodity = marketMapper.map(resource.data.commodity)
                    )
                    Resource.Success(market, resource.message)
                } catch (e: Exception) {
                    Resource.Error("Mapping Error: ${e.message}")
                }
            }
            is Resource.Error -> Resource.Error(resource.message)
        }
    }.flowOn(defaultDispatcher)
}
