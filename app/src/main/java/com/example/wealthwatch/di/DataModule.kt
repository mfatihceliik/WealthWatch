package com.example.wealthwatch.di

import com.example.wealthwatch.data.remote.crypto.CryptoDataSource
import com.example.wealthwatch.data.remote.SocketService
import com.example.wealthwatch.data.remote.SocketServiceImpl
import com.example.wealthwatch.data.remote.commodity.CommodityDataSource
import com.example.wealthwatch.data.remote.commodity.RemoteCommodityDataSource
import com.example.wealthwatch.data.remote.crypto.RemoteCryptoDataSource
import com.example.wealthwatch.data.remote.currency.ExchangeDataSource
import com.example.wealthwatch.data.remote.currency.RemoteExchangeDataSource
import com.example.wealthwatch.data.remote.market.MarketDataSource
import com.example.wealthwatch.data.remote.market.RemoteMarketDataSource
import com.example.wealthwatch.data.remote.stock.RemoteStockDataSource
import com.example.wealthwatch.data.remote.stock.StockDataSource
import com.example.wealthwatch.data.remote.search.RemoteSearchDataSource
import com.example.wealthwatch.data.remote.search.SearchDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WebSocketModule {
    @Binds
    @Singleton
    abstract fun bindSocket(impl: SocketServiceImpl): SocketService
}

@Module
@InstallIn(SingletonComponent::class)
abstract class CryptoDataModule {
    @Binds
    @Singleton
    abstract fun bindCryptoDataSource(impl: RemoteCryptoDataSource): CryptoDataSource

    @Binds
    @Singleton
    abstract fun bindCommodityDataSource(impl: RemoteCommodityDataSource): CommodityDataSource

    @Binds
    @Singleton
    abstract fun bindStockDataSource(impl: RemoteStockDataSource): StockDataSource

    @Binds
    @Singleton
    abstract fun bindCurrencyDataSource(impl: RemoteExchangeDataSource): ExchangeDataSource

    @Binds
    @Singleton
    abstract fun bindMarketDataSource(impl: RemoteMarketDataSource): MarketDataSource

    @Binds
    @Singleton
    abstract fun bindSearchDataSource(impl: RemoteSearchDataSource): SearchDataSource
}
