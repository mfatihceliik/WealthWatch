package com.example.wealthwatch.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.wealthwatch.data.local.entity.asset.AssetEntity
import com.example.wealthwatch.data.local.entity.asset.AssetWithTransactions
import com.example.wealthwatch.data.local.entity.transaction.TransactionEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface AssetDao {
    // 1. DÜZELTME: Dashboard için hem varlığı hem de işlemlerini liste olarak döner
    @Transaction
    @Query("SELECT * FROM assets")
    fun getAllAssetsWithTransactions(): Flow<List<AssetWithTransactions>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsset(asset: AssetEntity)
    @Update
    suspend fun updateAsset(asset: AssetEntity)
    @Delete
    suspend fun deleteAsset(asset: AssetEntity)
    @Query("SELECT * FROM assets WHERE symbol = :symbol LIMIT 1")
    suspend fun getAssetBySymbol(symbol: String): AssetEntity?
    @Insert
    suspend fun insertTransaction(transaction: TransactionEntity)
    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)
    @Transaction
    @Query("SELECT * FROM assets WHERE symbol = :symbol")
    fun getAssetHistory(symbol: String): Flow<AssetWithTransactions?>

    @Query("UPDATE assets SET totalAmount = :newAmount, averageCost = :newCost WHERE symbol = :symbol")
    suspend fun updateAssetStats(symbol: String, newAmount: Double, newCost: Double)

    @Query("SELECT * FROM transactions WHERE assetSymbol = :symbol")
    suspend fun getTransactionsBySymbol(symbol: String): List<TransactionEntity>
}
