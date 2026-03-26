package com.example.wealthwatch.domain.use_case.asset

import com.example.wealthwatch.domain.model.asset.AssetWithTransactions
import com.example.wealthwatch.domain.repository.local.asset.AssetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAssetsUseCase @Inject constructor(
    private val repository: AssetRepository
) {
    operator fun invoke(): Flow<List<AssetWithTransactions>> = repository.getAllAssetsWithTransactions()
}
