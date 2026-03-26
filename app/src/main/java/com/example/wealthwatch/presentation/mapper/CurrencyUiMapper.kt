package com.example.wealthwatch.presentation.mapper

import com.example.wealthwatch.core.mapper.BaseMapper
import com.example.wealthwatch.core.util.WealthWatchFormatter
import com.example.wealthwatch.domain.model.PriceTrend
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.domain.model.currency.Currency
import com.example.wealthwatch.domain.model.settings.AppCurrency
import com.example.wealthwatch.presentation.model.AssetUiModel
import javax.inject.Inject
import javax.inject.Singleton

import com.example.wealthwatch.di.DispatcherDefault
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@Singleton
class CurrencyUiMapper @Inject constructor(
    private val formatter: WealthWatchFormatter,
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher
) {

    data class Input(
        val currency: Currency,
        val isFavorite: Boolean = false,
        val appCurrency: AppCurrency = AppCurrency.USD
    )

    suspend fun map(input: Input): AssetUiModel = withContext(defaultDispatcher) {
        val (item, isFavorite, appCurrency) = input

        val price = item.price
        val change =
            item.change // Assuming Domain Currency 'change' captures the relevant delta (percent or abs)

        val trend = when {
            change > 0 -> PriceTrend.UP
            change < 0 -> PriceTrend.DOWN
            else -> PriceTrend.NEUTRAL
        }

        AssetUiModel(
            symbol = item.symbol,
            name = item.name,
            icon = item.iconUrl ?: "",
            type = item.type,
            price = formatter.formatCurrency(price, appCurrency),
            priceChangePercent = formatter.formatPercentage(change),
            trend = trend,
            isFavorite = isFavorite
        )
    }

    suspend operator fun invoke(
        currencies: List<Currency>, favSet: Set<String>, currency: AppCurrency
    ): List<AssetUiModel> {

        return currencies.map {
            map(
                Input(
                    currency = it, isFavorite = favSet.contains(it.symbol), appCurrency = currency
                )
            )
        }
    }
}
