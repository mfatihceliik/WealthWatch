package com.example.wealthwatch.presentation.mapper

import com.example.wealthwatch.core.mapper.BaseMapper
import com.example.wealthwatch.domain.model.PriceTrend
import com.example.wealthwatch.domain.model.crypto.Crypto
import com.example.wealthwatch.domain.model.settings.AppCurrency
import com.example.wealthwatch.presentation.model.AssetUiModel
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

import com.example.wealthwatch.core.util.WealthWatchFormatter
import com.example.wealthwatch.di.DispatcherDefault
import kotlinx.coroutines.CoroutineDispatcher

@Singleton
class CryptoUiMapper @Inject constructor(
    private val formatter: WealthWatchFormatter,
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher
) {

    data class Input(
        val crypto: Crypto,
        val isFavorite: Boolean = false,
        val currency: AppCurrency = AppCurrency.USD,
        val exchangeRate: Double = 1.0
    )

    suspend fun map(input: Input): AssetUiModel {
        return mapToCurrency(input.crypto, input.isFavorite, input.currency, input.exchangeRate)
    }

    suspend fun mapToNative(crypto: Crypto, isFavorite: Boolean = false): AssetUiModel {
        // Crypto is generally native to USD in our app context
        return mapToCurrency(crypto, isFavorite, AppCurrency.USD, 1.0)
    }

    suspend fun mapToCurrency(
        crypto: Crypto,
        isFavorite: Boolean,
        currency: AppCurrency,
        exchangeRate: Double
    ): AssetUiModel {
        return withContext(defaultDispatcher) {
            val changePercent = crypto.priceChangePercent.toDoubleOrNull() ?: 0.0
            val currentPrice = crypto.price
            val volume = crypto.volume.toDoubleOrNull() ?: 0.0
            // Convert logic
            val convertedPrice = currentPrice * exchangeRate
            val convertedPriceChange = (crypto.priceChange.toDoubleOrNull() ?: 0.0) * exchangeRate

            val trend = when {
                changePercent > 0 -> PriceTrend.UP
                changePercent < 0 -> PriceTrend.DOWN
                else -> PriceTrend.NEUTRAL
            }

            AssetUiModel(
                symbol = crypto.symbol,
                name = crypto.name,
                icon = crypto.iconUrl ?: "",
                type = crypto.type,
                quoteVolume = crypto.quoteVolume,
                price = formatter.formatCurrency(convertedPrice, currency),
                volume = formatter.formatVolume(volume),
                priceChangePercent = formatter.formatPercentage(changePercent),
                priceChange = formatter.formatCurrency(convertedPriceChange, currency),
                trend = trend,
                isFavorite = isFavorite
            )
        }
    }
}
