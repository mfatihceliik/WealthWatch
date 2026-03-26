package com.example.wealthwatch.data.local

import com.example.wealthwatch.data.local.dao.AssetDao
import com.example.wealthwatch.data.local.dao.WatchlistDao
import com.example.wealthwatch.data.local.entity.asset.AssetEntity
import com.example.wealthwatch.data.local.entity.asset.AssetWithTransactions
import com.example.wealthwatch.data.local.entity.transaction.TransactionEntity
import com.example.wealthwatch.data.local.entity.watchlist.WatchlistEntity
import com.example.wealthwatch.data.local.entity.search.SearchHistoryEntity
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class LocalDataSource @Inject constructor(
    private val assetDao: AssetDao,
    private val watchlistDao: WatchlistDao,
    private val searchHistoryDao: com.example.wealthwatch.data.local.dao.SearchHistoryDao
) {
    // ASSET
    fun getAllAssetsWithTransactions(): Flow<List<AssetWithTransactions>> = assetDao.getAllAssetsWithTransactions()
    suspend fun insertAsset(asset: AssetEntity) = assetDao.insertAsset(asset)
    suspend fun updateAsset(asset: AssetEntity) = assetDao.updateAsset(asset)
    suspend fun insertTransaction(transactionEntity: TransactionEntity) = assetDao.insertTransaction(transactionEntity)
    suspend fun getAssetBySymbol(symbol: String) = assetDao.getAssetBySymbol(symbol)
    fun getAssetHistory(symbol: String): Flow<AssetWithTransactions?> = assetDao.getAssetHistory(symbol)
    suspend fun deleteAsset(asset: AssetEntity) = assetDao.deleteAsset(asset)
    suspend fun deleteTransaction(transaction: TransactionEntity) = assetDao.deleteTransaction(transaction)
    suspend fun getTransactionsBySymbol(symbol: String) = assetDao.getTransactionsBySymbol(symbol)

    // WATCHLIST
    fun getWatchlist(): Flow<List<WatchlistEntity>> = watchlistDao.getWatchlist()
    suspend fun insertWatchlist(entity: WatchlistEntity) = watchlistDao.insert(entity)
    suspend fun deleteWatchlist(symbol: String) = watchlistDao.delete(symbol)
    fun isFavorite(symbol: String): Flow<Boolean> = watchlistDao.isFavorite(symbol)

    // SEARCH HISTORY
    fun getSearchHistory() = searchHistoryDao.getAll()
    suspend fun insertSearchHistory(query: String) = searchHistoryDao.insert(
        SearchHistoryEntity(query)
    )
    suspend fun deleteSearchHistory(query: String) = searchHistoryDao.delete(query)
    suspend fun clearSearchHistory() = searchHistoryDao.clear()
}
