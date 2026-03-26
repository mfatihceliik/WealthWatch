package com.example.wealthwatch.domain.use_case.portfolio

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.domain.model.portfolio.PieChartSegment
import com.example.wealthwatch.domain.model.portfolio.PortfolioDashboard
import com.example.wealthwatch.domain.use_case.asset.GetAssetsUseCase
import com.example.wealthwatch.domain.use_case.market.GetExchangeRatesUseCase
import com.example.wealthwatch.domain.use_case.market.GetMarketStreamUseCase
import com.example.wealthwatch.domain.use_case.settings.GetCurrencyUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetPortfolioDashboardUseCase @Inject constructor(
    private val getAssetsUseCase: GetAssetsUseCase,
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val getExchangeRatesUseCase: GetExchangeRatesUseCase,
    private val getMarketStreamUseCase: GetMarketStreamUseCase,
) {
    operator fun invoke(): Flow<Resource<PortfolioDashboard>> {
        return combine(
            getAssetsUseCase(),
            getCurrencyUseCase(),
            getExchangeRatesUseCase(),
            getMarketStreamUseCase()
        ) { assetsWithTx, userCurrency, rates, marketSocketState ->

            val marketData = if (marketSocketState is SocketState.Connected) {
                marketSocketState.data.associateBy { it.symbol }
            } else {
                emptyMap()
            }

            val rate = 1.0

            var totalBalanceInUsd = 0.0
            
            val domainAssetsUpdated = assetsWithTx.map { assetWithTx ->
                val asset = assetWithTx.asset
                val liveData = marketData[asset.marketAsset.symbol]
                val currentPrice = liveData?.price ?: asset.marketAsset.currentPrice
                
                totalBalanceInUsd += asset.totalAmount * currentPrice
                asset.copy(marketAsset = asset.marketAsset.copy(currentPrice = currentPrice))
            }
            
            val totalBalanceConverted = totalBalanceInUsd * rate

            val sortedAssets = domainAssetsUpdated.sortedByDescending { it.totalValue }
            val pieChartSegments = if (totalBalanceInUsd > 0) {
                val topAssets = sortedAssets.take(5)
                val otherAssets = sortedAssets.drop(5)
                
                val segments = ArrayList<PieChartSegment>()
                
                topAssets.forEach { asset ->
                     val value = (asset.totalValue * rate)
                     segments.add(PieChartSegment(value, asset.marketAsset.symbol))
                }
                
                if (otherAssets.isNotEmpty()) {
                    val otherValue = otherAssets.sumOf { it.totalValue } * rate
                    segments.add(PieChartSegment(otherValue, "Other"))
                }
                segments
            } else {
                listOf(PieChartSegment(100.0, "Empty"))
            }

            Resource.Success(
                PortfolioDashboard(
                    assets = domainAssetsUpdated,
                    totalBalance = totalBalanceConverted,
                    currency = userCurrency,
                    pieChartData = pieChartSegments
                )
            )
        }
    }
}
