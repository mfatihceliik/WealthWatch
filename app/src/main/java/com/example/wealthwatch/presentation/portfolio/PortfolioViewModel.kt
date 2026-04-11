package com.example.wealthwatch.presentation.portfolio

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.WealthWatchFormatter
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.domain.model.asset.PortfolioAsset
import com.example.wealthwatch.domain.model.portfolio.PortfolioDashboard
import com.example.wealthwatch.domain.model.settings.AppCurrency
import com.example.wealthwatch.domain.repository.local.settings.SettingsRepository
import com.example.wealthwatch.domain.use_case.portfolio.GetPortfolioDashboardUseCase
import com.example.wealthwatch.presentation.base.BaseViewModel
import com.example.wealthwatch.presentation.base.ScreenState
import com.example.wealthwatch.presentation.mapper.AssetUiMapper
import com.example.wealthwatch.presentation.model.AssetUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val getPortfolioDashboardUseCase: GetPortfolioDashboardUseCase,
    private val settingsRepository: SettingsRepository,
    private val assetUiMapper: AssetUiMapper,
    private val formatter: WealthWatchFormatter
) : BaseViewModel() {

    // Cache domain assets for toggling without re-fetching
    private var domainAssets: List<PortfolioAsset> = emptyList()

    private val _uiState = MutableStateFlow(PortfolioUiState())
    val uiState: StateFlow<PortfolioUiState> = _uiState.asStateFlow()

    init {
        collectPortfolioData()
    }

    fun onEvent(event: PortfolioUiEvent) {
        when (event) {
            is PortfolioUiEvent.OnToggleChartMode -> {
                togglePieChartMode()
            }
            is PortfolioUiEvent.OnCategoryClick -> {
                val currentExpanded = _uiState.value.expandedCategories
                val category = event.category
                val newExpanded = if (currentExpanded.contains(category)) {
                    currentExpanded - category
                } else {
                    currentExpanded + category
                }
                _uiState.update { it.copy(expandedCategories = newExpanded) }
            }
            is PortfolioUiEvent.OnCurrencyChange -> {
                setCurrency(event.currency)
            }
            is PortfolioUiEvent.OnAssetClick -> {
            }
        }
    }

    private fun setCurrency(currency: AppCurrency) {
        launch { settingsRepository.setCurrency(currency) }
    }

    private fun togglePieChartMode() {
        val currentGrouped = _uiState.value.isPieChartGrouped
        _uiState.update { it.copy(isPieChartGrouped = !currentGrouped) }
        calculatePieChartData(domainAssets, !currentGrouped, _uiState.value.currency)
    }

    private fun collectPortfolioData() {
        launch {
            getPortfolioDashboardUseCase().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(screenState = ScreenState.LOADING) }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                screenState = ScreenState.ERROR,
                            )
                        }
                    }

                    is Resource.Success -> {
                        updateUiWithDashboard(resource.data)
                    }
                }
            }
        }
    }

    private suspend fun updateUiWithDashboard(dashboard: PortfolioDashboard) {
        val (assets, totalBalance, currency, _) = dashboard
        domainAssets = assets

        // Map Assets to UI Model
        val uiAssets = mutableListOf<AssetUiModel>()
        assets.forEach { asset ->
            uiAssets.add(
                assetUiMapper.map(
                    AssetUiMapper.Input(
                        asset = asset,
                        currency = currency,
                        exchangeRate = 1.0
                    )
                )
            )
        }
        

        // Group Assets by Type (Pre-calculation for UI)
        val sections = AssetType.entries.mapNotNull { type ->
            val assetsInType = uiAssets.filter { it.type == type }
            if (assetsInType.isNotEmpty()) {
                PortfolioAssetSection(type = type, assets = assetsInType)
            } else null
        }

        _uiState.update {
            it.copy(
                screenState = ScreenState.HAS_DATA,
                assets = uiAssets,
                sections = sections,
                totalBalance = totalBalance,
                currency = currency,
                exchangeRate = 1.0,
            )
        }
        
        // Calculate Pie Chart Data
        calculatePieChartData(assets, _uiState.value.isPieChartGrouped, currency)
    }

    private fun calculatePieChartData(assets: List<PortfolioAsset>, isGrouped: Boolean, currency: AppCurrency) {
        // 1. Generate Raw Data
        val rawData = if (isGrouped) {
             // Group by AssetType
             val groupedMap = assets.groupBy { it.marketAsset.type }
             groupedMap.mapNotNull { (type, assetList) ->
                 val totalValue = assetList.sumOf { it.totalValue }
                 if (totalValue <= 0) null else PortfolioPieChartData(
                     value = totalValue,
                     label = type.name, 
                     category = type,
                     titleResId = -1
                 )
             }
        } else {
            // Individual Assets
            assets.mapNotNull { asset ->
                 val totalValue = asset.totalValue
                 if (totalValue <= 0) null else PortfolioPieChartData(
                    value = totalValue,
                    label = asset.marketAsset.symbol,
                    category = asset.marketAsset.type
                )
            }
        }.sortedByDescending { it.value }

        // 2. Calculate Percentages & Formatting
        val totalValue = rawData.sumOf { it.value }
        val finalData = rawData.map { item ->
            val percent = if (totalValue > 0) (item.value / totalValue) * 100 else 0.0
            item.copy(
                formattedPercentage = formatter.formatDistribution(percent),
                formattedValue = formatter.formatCurrency(item.value, currency)
            )
        }
        
        val formattedTotal = formatter.formatCurrency(totalValue, currency)

         _uiState.update { 
             it.copy(
                 pieChartData = finalData,
                 formattedTotalBalance = formattedTotal
             ) 
         }
    }
}
