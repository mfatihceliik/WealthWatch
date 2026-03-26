package com.example.wealthwatch.domain.use_case.market

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.domain.model.stock.Stock
import com.example.wealthwatch.domain.repository.remote.stock.StockRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetStockMarketUseCase @Inject constructor(
    private val repository: StockRepository
) {
    operator fun invoke(): Flow<Resource<List<Stock>>> = repository.getUsStocks()
}
