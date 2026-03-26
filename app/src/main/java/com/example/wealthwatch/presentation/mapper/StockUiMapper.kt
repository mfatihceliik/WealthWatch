package com.example.wealthwatch.presentation.mapper

import com.example.wealthwatch.core.mapper.BaseMapper
import com.example.wealthwatch.domain.model.PriceTrend
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.domain.model.settings.AppCurrency
import com.example.wealthwatch.domain.model.stock.Stock
import com.example.wealthwatch.presentation.model.AssetUiModel
import javax.inject.Inject
import javax.inject.Singleton

import com.example.wealthwatch.core.util.WealthWatchFormatter
import com.example.wealthwatch.di.DispatcherDefault
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@Singleton
class StockUiMapper @Inject constructor(
    private val formatter: WealthWatchFormatter,
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher
) {

    data class Input(
        val stock: Stock,
        val isFavorite: Boolean = false,
        val currency: AppCurrency = AppCurrency.USD
    )

    suspend fun map(input: Input): AssetUiModel = withContext(defaultDispatcher) {
        val (stock, isFavorite, currency) = input
        val price = stock.price

        val trend = when {
            stock.change > 0 -> PriceTrend.UP
            stock.change < 0 -> PriceTrend.DOWN
            else -> PriceTrend.NEUTRAL
        }

        AssetUiModel(
            symbol = stock.symbol,
            name = stock.name,
            icon = stock.iconUrl ?: "",
            type = stock.type,
            volume = formatter.formatVolume(stock.volume),
            price = formatter.formatCurrency(price, currency),
            priceChangePercent = formatter.formatPercentage(stock.change),
            trend = trend,
            isFavorite = isFavorite
        )
    }

    suspend operator fun invoke(
        stocks: List<Stock>, favSet: Set<String>, currency: AppCurrency
    ): List<AssetUiModel> {

        return stocks.map {
            map(Input(stock = it, isFavorite = favSet.contains(it.symbol), currency = currency))
        }
    }
}
