package com.example.wealthwatch.domain.use_case.asset

import com.example.wealthwatch.domain.model.asset.PortfolioAsset
import com.example.wealthwatch.domain.repository.local.asset.AssetRepository
import javax.inject.Inject

class DeleteAssetUseCase @Inject constructor(
    private val repository: AssetRepository
) {
    suspend operator fun invoke(asset: PortfolioAsset) {
        repository.deleteAsset(asset)
    }
}
