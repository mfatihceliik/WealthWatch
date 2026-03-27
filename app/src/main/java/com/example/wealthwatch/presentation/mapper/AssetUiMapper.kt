package com.example.wealthwatch.presentation.mapper

import com.example.wealthwatch.core.mapper.BaseMapper
import com.example.wealthwatch.domain.model.PriceTrend
import com.example.wealthwatch.domain.model.asset.PortfolioAsset
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.domain.model.settings.AppCurrency
import com.example.wealthwatch.presentation.model.AssetUiModel
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

import com.example.wealthwatch.core.util.WealthWatchFormatter
import com.example.wealthwatch.di.DispatcherDefault
import kotlinx.coroutines.CoroutineDispatcher

@Singleton
class AssetUiMapper @Inject constructor(
    private val formatter: WealthWatchFormatter,
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher
) {

    data class Input(
        val asset: PortfolioAsset,
        val currency: AppCurrency = AppCurrency.USD,
        val exchangeRate: Double,
        val isFavorite: Boolean = false
    )

    suspend fun map(input: Input): AssetUiModel {
        return mapToCurrency(input.asset, input.currency, input.exchangeRate, input.isFavorite)
    }

    suspend fun mapToNative(asset: PortfolioAsset, isFavorite: Boolean = false): AssetUiModel {
        val (nativeCurrency, rate) = when (asset.marketAsset.type.name.uppercase()) {
            "BIST" -> Pair(AppCurrency.TRY, 1.0)
            "US_STOCK", "CRYPTO", "COMMODITY" -> Pair(AppCurrency.USD, 1.0)
            else -> Pair(AppCurrency.USD, 1.0) 
        }
        return mapToCurrency(asset, nativeCurrency, rate, isFavorite)
    }

    suspend fun mapToNative(asset: MarketAsset, isFavorite: Boolean = false): AssetUiModel {
        val (nativeCurrency, rate) = when (asset.type.name.uppercase()) {
            "BIST" -> Pair(AppCurrency.TRY, 1.0)
            "US_STOCK", "CRYPTO", "COMMODITY" -> Pair(AppCurrency.USD, 1.0)
            else -> Pair(AppCurrency.USD, 1.0) 
        }
        return mapToCurrency(asset, nativeCurrency, rate, isFavorite)
    }

    suspend fun mapToCurrency(
        asset: MarketAsset,
        currency: AppCurrency,
        exchangeRate: Double,
        isFavorite: Boolean = false
    ): AssetUiModel {
        return withContext(defaultDispatcher) {
            try {
                val trend = when {
                    asset.priceChangePercent > 0 -> PriceTrend.UP
                    asset.priceChangePercent < 0 -> PriceTrend.DOWN
                    else -> PriceTrend.NEUTRAL
                }

                AssetUiModel(
                    symbol = asset.symbol,
                    name = asset.name,
                    icon = asset.icon,
                    type = asset.type,
                    amount = 0.0,
                    priceChange = formatter.formatPriceChange(asset.priceChange, currency),
                    priceChangePercent = formatter.formatPercentage(asset.priceChangePercent),
                    volume = formatter.formatVolume(asset.volume),
                    price = formatter.formatCurrency(asset.currentPrice, currency),
                    formattedAmount = formatter.formatAmount(0.0),
                    formattedTotalValue = formatter.formatCurrency(0.0, currency),
                    formattedAverageCost = formatter.formatCurrency(0.0, currency),
                    formattedProfitLoss = formatter.formatPercentage(0.0),
                    formattedProfitLossValue = formatter.formatProfitLossValue(0.0, currency),
                    isProfit = false,
                    trend = trend,
                    isFavorite = isFavorite
                )
            } catch (e: Exception) {
                AssetUiModel()
            }
        }
    }

    suspend fun mapToCurrency(
        asset: PortfolioAsset,
        currency: AppCurrency,
        exchangeRate: Double,
        isFavorite: Boolean = false
    ): AssetUiModel {
        return withContext(defaultDispatcher) {
            try {
                val totalValue = asset.totalValue * exchangeRate
                val profitLoss = asset.totalProfitLoss * exchangeRate
                val averageCostConverted = asset.averageCost * exchangeRate

                val trend = if (asset.totalAmount > 0 && asset.averageCost > 0) {
                    when {
                        profitLoss > 0 -> PriceTrend.UP
                        profitLoss < 0 -> PriceTrend.DOWN
                        else -> PriceTrend.NEUTRAL
                    }
                } else {
                    when {
                        asset.marketAsset.priceChangePercent > 0 -> PriceTrend.UP
                        asset.marketAsset.priceChangePercent < 0 -> PriceTrend.DOWN
                        else -> PriceTrend.NEUTRAL
                    }
                }

                AssetUiModel(
                    symbol = asset.marketAsset.symbol,
                    name = asset.marketAsset.name,
                    icon = asset.marketAsset.icon,
                    type = asset.marketAsset.type,
                    amount = asset.totalAmount,
                    priceChange = formatter.formatPriceChange(asset.marketAsset.priceChange, currency),
                    priceChangePercent = formatter.formatPercentage(asset.marketAsset.priceChangePercent),
                    volume = formatter.formatVolume(asset.marketAsset.volume),
                    price = formatter.formatCurrency(asset.marketAsset.currentPrice, currency),
                    formattedAmount = formatter.formatAmount(asset.totalAmount),
                    formattedTotalValue = formatter.formatCurrency(totalValue, currency),
                    formattedAverageCost = formatter.formatCurrency(
                        averageCostConverted, currency
                    ),
                    formattedProfitLoss = formatter.formatPercentage(asset.profitLossPercentage),
                    formattedProfitLossValue = formatter.formatProfitLossValue(
                        profitLoss, currency
                    ),
                    isProfit = profitLoss >= 0,
                    trend = trend,
                    isFavorite = isFavorite
                )
            } catch (e: Exception) {
                AssetUiModel()
            }
        }
    }
}
