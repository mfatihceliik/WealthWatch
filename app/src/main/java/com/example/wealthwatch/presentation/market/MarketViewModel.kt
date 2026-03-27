package com.example.wealthwatch.presentation.market

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.domain.model.market.MarketDashboard
import com.example.wealthwatch.domain.model.settings.AppCurrency
import com.example.wealthwatch.domain.repository.local.settings.SettingsRepository
import com.example.wealthwatch.domain.use_case.asset.GetAssetsUseCase
import com.example.wealthwatch.domain.use_case.market.GetExchangeRatesUseCase
import com.example.wealthwatch.domain.use_case.market.GetMarketOverviewUseCase
import com.example.wealthwatch.domain.use_case.settings.GetCurrencyUseCase
import com.example.wealthwatch.presentation.base.BaseViewModel
import com.example.wealthwatch.presentation.base.ScreenState
import com.example.wealthwatch.presentation.mapper.AssetUiMapper
import com.example.wealthwatch.presentation.model.AssetUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val getMarketOverviewUseCase: GetMarketOverviewUseCase,
    private val getAssetsUseCase: GetAssetsUseCase,
    private val getExchangeRatesUseCase: GetExchangeRatesUseCase,
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val settingsRepository: SettingsRepository,
    private val assetUiMapper: AssetUiMapper,
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<MarketUiState> = MutableStateFlow(MarketUiState())
    val uiState: StateFlow<MarketUiState> = _uiState.asStateFlow()

    init {
        collectMarketData()
    }

    fun onEvent(event: MarketUiEvent) {
        when (event) {
            is MarketUiEvent.OnCurrencyChange -> {
                updateCurrency(event.currency)
            }

            is MarketUiEvent.OnRetry -> {
                onRetry()
            }
        }
    }

    private fun updateCurrency(currency: AppCurrency) = launch {
        settingsRepository.setCurrency(currency)
    }

    private fun collectMarketData() = launch {
        combine(
            getMarketOverviewUseCase(),
            getAssetsUseCase(),
            getExchangeRatesUseCase(),
            getCurrencyUseCase()
        ) { marketResource, assets, rates, currency ->
            when (marketResource) {
                is Resource.Loading -> Resource.Loading
                is Resource.Error -> Resource.Error(marketResource.message)
                is Resource.Success -> {
                    val rate = rates[currency.code] ?: 1.0

                    var totalBalanceInUsd = 0.0
                    assets.forEach {
                        totalBalanceInUsd += it.asset.totalAmount * it.asset.marketAsset.currentPrice
                    }
                    val totalBalance = totalBalanceInUsd * rate

                    Resource.Success(
                        MarketDashboard(
                            market = marketResource.data,
                            assets = assets,
                            rates = rates,
                            currency = currency,
                            totalBalance = totalBalance
                        )
                    )
                }
            }
        }.flowOn(Dispatchers.Default).catch { e ->
            e.printStackTrace()
            _uiState.update {
                it.copy(
                    screenState = ScreenState.ERROR, message = e.message ?: "Unknown Error"
                )
            }
        }.collect { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _uiState.update { it.copy(screenState = ScreenState.LOADING) }
                }

                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            screenState = ScreenState.ERROR, message = resource.message
                        )
                    }
                }

                is Resource.Success -> {
                    updateUiWithDashboard(resource.data)
                }
            }
        }
    }

    private suspend fun updateUiWithDashboard(data: MarketDashboard) =
        withContext(Dispatchers.Default) {
            val (market, _, _, currency, totalBalance) = data

            try {
                // To call suspend mapToNative within map, we must use a context that supports it, like coroutineScope
                // or just call mapToNative directly in a loop/map if it were not suspend.
                // Since it is suspend, we use a custom extension or loop.
                
                suspend fun List<com.example.wealthwatch.domain.model.asset.MarketAsset>.mapToUi(): List<AssetUiModel> {
                    return this.map { assetUiMapper.mapToNative(it) }
                }

                val pulseDeferred = async { market.pulse.mapToUi() }
                val cryptoGainersDeferred = async { market.crypto.gainers.mapToUi() }
                val cryptoLosersDeferred = async { market.crypto.losers.mapToUi() }
                val usStockGainersDeferred = async { market.usStock.gainers.mapToUi() }
                val usStockLosersDeferred = async { market.usStock.losers.mapToUi() }
                val trStockGainersDeferred = async { market.trStock.gainers.mapToUi() }
                val trStockLosersDeferred = async { market.trStock.losers.mapToUi() }
                val currencyGainersDeferred = async { market.currency.gainers.mapToUi() }
                val currencyLosersDeferred = async { market.currency.losers.mapToUi() }
                val commodityGainersDeferred = async { market.commodity.gainers.mapToUi() }
                val commodityLosersDeferred = async { market.commodity.losers.mapToUi() }

                _uiState.update {
                    it.copy(
                        screenState = ScreenState.HAS_DATA,
                        message = "",
                        currency = currency,
                        portfolioBalance = totalBalance,
                        marketPulse = pulseDeferred.await(),
                        cryptoGainers = cryptoGainersDeferred.await(),
                        cryptoLosers = cryptoLosersDeferred.await(),
                        usStockGainers = usStockGainersDeferred.await(),
                        usStockLosers = usStockLosersDeferred.await(),
                        trStockGainers = trStockGainersDeferred.await(),
                        trStockLosers = trStockLosersDeferred.await(),
                        currencyGainers = currencyGainersDeferred.await(),
                        currencyLosers = currencyLosersDeferred.await(),
                        commodityGainers = commodityGainersDeferred.await(),
                        commodityLosers = commodityLosersDeferred.await(),
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        screenState = ScreenState.ERROR, message = "Mapping Error: ${e.message}"
                    )
                }
            }
        }

    fun onRetry() {
        collectMarketData()
    }
}
