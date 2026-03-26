package com.example.wealthwatch.di

import com.example.wealthwatch.data.remote.commodity.CommodityApiService
import com.example.wealthwatch.data.remote.crypto.CryptoApiService
import com.example.wealthwatch.data.remote.currency.CurrencyApiService
import com.example.wealthwatch.data.remote.stock.StockApiService
import com.example.wealthwatch.data.remote.market.MarketApiService
import com.example.wealthwatch.data.remote.search.SearchApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.socket.client.Socket
import jakarta.inject.Qualifier
import jakarta.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideSocketIo(@BaseUrl baseUrl: String): Socket {
        return try {
            val options = io.socket.client.IO.Options().apply {
                forceNew = true
                reconnection = true
            }
            io.socket.client.IO.socket(baseUrl, options)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Provides
    @Singleton
    @RestClient
    fun provideRestClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS).addInterceptor(loggingInterceptor).build()

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = "http://10.0.2.2:3000/"

    @Provides
    @RestUrl
    fun provideRestUrl(@BaseUrl baseUrl: String): String = "${baseUrl}api/"

    @Provides
    @Singleton
    @WWRetrofit
    fun provideCryptoRetrofit(
        @RestClient client: OkHttpClient, json: Json, @RestUrl baseUrl: String
    ): Retrofit = Retrofit.Builder().baseUrl(baseUrl).client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType())).build()

    @Provides
    @Singleton
    fun provideCryptoApiService(@WWRetrofit retrofit: Retrofit): CryptoApiService =
        retrofit.create(CryptoApiService::class.java)

    @Provides
    @Singleton
    fun provideStockApiService(@WWRetrofit retrofit: Retrofit): StockApiService =
        retrofit.create(StockApiService::class.java)

    @Provides
    @Singleton
    fun provideCommodityApiService(@WWRetrofit retrofit: Retrofit): CommodityApiService =
        retrofit.create(CommodityApiService::class.java)

    @Provides
    @Singleton
    fun provideCurrencyApiService(@WWRetrofit retrofit: Retrofit): CurrencyApiService =
        retrofit.create(CurrencyApiService::class.java)

    @Provides
    @Singleton
    fun provideMarketApiService(@WWRetrofit retrofit: Retrofit): MarketApiService =
        retrofit.create(MarketApiService::class.java)

    @Provides
    @Singleton
    fun provideSearchApiService(@WWRetrofit retrofit: Retrofit): SearchApiService =
        retrofit.create(SearchApiService::class.java)
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RestClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RestUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WWRetrofit
