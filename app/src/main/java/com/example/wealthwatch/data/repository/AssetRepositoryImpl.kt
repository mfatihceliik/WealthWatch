package com.example.wealthwatch.data.repository

import com.example.wealthwatch.data.local.LocalDataSource
import com.example.wealthwatch.data.mapper.AssetMapper
import com.example.wealthwatch.data.mapper.AssetWithTransactionMapper
import com.example.wealthwatch.domain.model.asset.PortfolioAsset
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.domain.model.asset.AssetWithTransactions
import com.example.wealthwatch.domain.repository.local.asset.AssetRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AssetRepositoryImpl @Inject constructor(
    private val local: LocalDataSource,
    private val assetWithTransactionMapper: AssetWithTransactionMapper,
    private val assetMapper: AssetMapper
) : AssetRepository {
    override fun getAllAssetsWithTransactions(): Flow<List<AssetWithTransactions>> =
        local.getAllAssetsWithTransactions().map { list -> assetWithTransactionMapper(list) }

    override suspend fun insertAsset(asset: PortfolioAsset) =
        local.insertAsset(assetMapper.mapToEntity(asset))

    override suspend fun updateAsset(asset: PortfolioAsset) =
        local.updateAsset(assetMapper.mapToEntity(asset))

    override suspend fun getAsset(symbol: String): PortfolioAsset? {
        val entity = local.getAssetBySymbol(symbol) ?: return null
        val marketAsset = MarketAsset(
            symbol = entity.symbol,
            name = entity.name,
            icon = entity.icon,
            type = entity.type,
            currentPrice = entity.currentPrice,
            priceChange = 0.0,
            priceChangePercent = 0.0,
            volume = 0.0
        )
        return assetMapper.map(entity, marketAsset)
    }

    override fun getAssetHistory(symbol: String): Flow<AssetWithTransactions?> =
        local.getAssetHistory(symbol).map {
            it?.let { entity -> assetWithTransactionMapper(entity) }
        }

    override suspend fun deleteAsset(asset: PortfolioAsset) =
        local.deleteAsset(assetMapper.mapToEntity(asset))
}
