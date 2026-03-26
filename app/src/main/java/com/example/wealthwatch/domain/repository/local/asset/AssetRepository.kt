package com.example.wealthwatch.domain.repository.local.asset

import com.example.wealthwatch.domain.model.asset.PortfolioAsset
import com.example.wealthwatch.domain.model.asset.AssetWithTransactions
import kotlinx.coroutines.flow.Flow

interface AssetRepository {
    fun getAllAssetsWithTransactions(): Flow<List<AssetWithTransactions>>
    fun getAssetHistory(symbol: String): Flow<AssetWithTransactions?>
    suspend fun getAsset(symbol: String): PortfolioAsset?
    suspend fun insertAsset(asset: PortfolioAsset)
    suspend fun updateAsset(asset: PortfolioAsset)
    suspend fun deleteAsset(asset: PortfolioAsset)
}