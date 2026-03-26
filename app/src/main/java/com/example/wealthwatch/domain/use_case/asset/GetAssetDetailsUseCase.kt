package com.example.wealthwatch.domain.use_case.asset

import com.example.wealthwatch.domain.model.asset.AssetWithTransactions
import com.example.wealthwatch.domain.repository.local.asset.AssetRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetAssetDetailsUseCase @Inject constructor(
    private val repository: AssetRepository
) {
    operator fun invoke(symbol: String): Flow<AssetWithTransactions?> = repository.getAssetHistory(symbol)
}
