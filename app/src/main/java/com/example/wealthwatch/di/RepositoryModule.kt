package com.example.wealthwatch.di

import com.example.wealthwatch.data.repository.AssetRepositoryImpl
import com.example.wealthwatch.data.repository.CommodityRepositoryImpl
import com.example.wealthwatch.data.repository.CryptoRepositoryImpl
import com.example.wealthwatch.data.repository.ExchangeRepositoryImpl
import com.example.wealthwatch.data.repository.SearchHistoryRepositoryImpl
import com.example.wealthwatch.data.repository.SettingsRepositoryImpl
import com.example.wealthwatch.data.repository.StockRepositoryImpl
import com.example.wealthwatch.data.repository.TransactionRepositoryImpl
import com.example.wealthwatch.data.repository.WatchlistRepositoryImpl
import com.example.wealthwatch.domain.repository.local.asset.AssetRepository
import com.example.wealthwatch.domain.repository.local.search_history.SearchHistoryRepository
import com.example.wealthwatch.domain.repository.local.settings.SettingsRepository
import com.example.wealthwatch.domain.repository.local.transaction.TransactionRepository
import com.example.wealthwatch.domain.repository.local.watchlist.WatchlistRepository
import com.example.wealthwatch.domain.repository.remote.commodity.CommodityRepository
import com.example.wealthwatch.domain.repository.remote.crypto.CryptoRepository
import com.example.wealthwatch.domain.repository.remote.currency.ExchangeRepository
import com.example.wealthwatch.domain.repository.remote.stock.StockRepository
import com.example.wealthwatch.data.repository.MarketRepositoryImpl
import com.example.wealthwatch.domain.repository.remote.market.MarketRepository
import com.example.wealthwatch.data.repository.SearchRepositoryImpl
import com.example.wealthwatch.domain.repository.remote.search.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CryptoRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRepository(cryptoRepositoryImpl: CryptoRepositoryImpl): CryptoRepository

    @Binds
    @Singleton
    abstract fun bindStockRepository(stockRepositoryImpl: StockRepositoryImpl): StockRepository

    @Binds
    @Singleton
    abstract fun bindCommodityRepository(commodityRepositoryImpl: CommodityRepositoryImpl): CommodityRepository

    @Binds
    @Singleton
    abstract fun bindCurrencyRepository(currencyRepositoryImpl: ExchangeRepositoryImpl): ExchangeRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    @Singleton
    abstract fun bindWatchlistRepository(watchlistRepositoryImpl: WatchlistRepositoryImpl): WatchlistRepository

    @Binds
    @Singleton
    abstract fun bindAssetRepository(assetRepositoryImpl: AssetRepositoryImpl): AssetRepository

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(transactionRepositoryImpl: TransactionRepositoryImpl): TransactionRepository

    @Binds
    @Singleton
    abstract fun bindSearchHistoryRepository(searchHistoryRepositoryImpl: SearchHistoryRepositoryImpl): SearchHistoryRepository

    @Binds
    @Singleton
    abstract fun bindMarketRepository(marketRepositoryImpl: MarketRepositoryImpl): MarketRepository

    @Binds
    @Singleton
    abstract fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository

}
