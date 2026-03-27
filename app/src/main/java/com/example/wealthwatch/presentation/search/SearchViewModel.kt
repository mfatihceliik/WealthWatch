package com.example.wealthwatch.presentation.search

import androidx.lifecycle.viewModelScope
import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.domain.model.settings.AppCurrency
import com.example.wealthwatch.domain.repository.local.search_history.SearchHistoryRepository
import com.example.wealthwatch.domain.repository.local.settings.SettingsRepository
import com.example.wealthwatch.domain.repository.remote.crypto.CryptoRepository
import com.example.wealthwatch.domain.repository.remote.stock.StockRepository
import com.example.wealthwatch.domain.use_case.watchlist.GetWatchlistStreamUseCase
import com.example.wealthwatch.domain.use_case.watchlist.ToggleWatchlistUseCase
import com.example.wealthwatch.presentation.base.BaseViewModel
import com.example.wealthwatch.presentation.base.ScreenState
import com.example.wealthwatch.presentation.mapper.AssetUiMapper
import com.example.wealthwatch.presentation.model.AssetUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val cryptoRepository: CryptoRepository,
    private val stockRepository: StockRepository,
    private val repository: SearchHistoryRepository,
    private val getWatchlistStreamUseCase: GetWatchlistStreamUseCase,
    private val toggleWatchlistUseCase: ToggleWatchlistUseCase,
    private val settingsRepository: SettingsRepository,
    private val assetUiMapper: AssetUiMapper
) : BaseViewModel() {

    private var searchJob: Job? = null

    // UI State
    private val _uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    // Internal Data Caches
    private var allTickers: List<MarketAsset> = emptyList()
    private var favoriteSet: Set<String> = emptySet()

    init {
        collectData()
        observeSearchHistory()
    }

    private data class FavoritesData(val favorites: Set<String>, val currency: AppCurrency)

    private fun collectData() {
        launch {
            combine(
                cryptoRepository.getCryptoTickers(),
                getWatchlistStreamUseCase(),
                settingsRepository.getCurrency(),
            ) { marketResult, watchlist, currency ->
                val favorites = watchlist.map { it.symbol }.toSet()

                Pair(marketResult, FavoritesData(favorites, currency))
            }.collect { (marketResult, favData) ->
                val (favorites, currency) = favData
                favoriteSet = favorites

                _uiState.update { it.copy(favorites = favorites, currency = currency) }

                when (marketResult) {
                    is Resource.Success -> {
                        allTickers = marketResult.data
                        if (allTickers.isEmpty()) {
                            _uiState.update { it.copy(screenState = ScreenState.NO_DATA) }
                        } else {
                            _uiState.update { it.copy(screenState = ScreenState.HAS_DATA) }
                            updateSearchList()
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                screenState = ScreenState.ERROR, message = marketResult.message
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _uiState.update { it.copy(screenState = ScreenState.LOADING) }
                    }
                }
            }
        }
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnQueryChanged -> handleQueryChanged(event.query)
            is SearchEvent.OnClearQuery -> handleQueryChanged("")
            is SearchEvent.OnHistoryItemClicked -> handleQueryChanged(event.query)
            is SearchEvent.OnDeleteHistoryItem -> deleteHistoryItem(event.query)
            is SearchEvent.OnClearAllHistory -> clearAllHistory()
            is SearchEvent.OnToggleFavorite -> toggleFavorite(event.crypto)
        }
    }

    private fun observeSearchHistory() {
        launch {
            repository.getSearchHistory().collect { historyList ->
                _uiState.update { it.copy(searchHistory = historyList.map { item -> item.query }) }
            }
        }
    }

    private fun deleteHistoryItem(query: String) {
        launch { repository.deleteSearchHistory(query) }
    }

    private fun clearAllHistory() {
        launch { repository.clearSearchHistory() }
    }

    private fun handleQueryChanged(query: String) {
        _uiState.update { it.copy(query = query) }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300L)
            updateSearchList()
        }
    }

    private fun updateSearchList() {
        val query = _uiState.value.query
        val currency = _uiState.value.currency

        if (query.isNotEmpty()) {
            searchJob = launch(Dispatchers.IO) {
                // 1. Filter Crypto Locally
                val filteredCryptos = allTickers.filter { it.symbol.contains(query, ignoreCase = true) }
                val cryptoUiModels = filteredCryptos.map {
                    assetUiMapper.mapToCurrency(
                        asset = it,
                        currency = currency,
                        exchangeRate = 1.0, 
                        isFavorite = favoriteSet.contains(it.symbol)
                    )
                }

                // Initial update with locally filtered cryptos
                _uiState.update { it.copy(cryptoList = cryptoUiModels) }

                // 2. Search Stocks Remotely
                stockRepository.searchStocks(query).collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            val stocks = resource.data ?: emptyList()
                            val stockUiModels = mutableListOf<AssetUiModel>()
                            stocks.forEach { stock ->
                                stockUiModels.add(
                                    assetUiMapper.mapToCurrency(
                                        asset = stock,
                                        currency = currency,
                                        exchangeRate = 1.0,
                                        isFavorite = favoriteSet.contains(stock.symbol)
                                    )
                                )
                            }
                            val combinedList = cryptoUiModels + stockUiModels
                            _uiState.update { it.copy(cryptoList = combinedList) }
                        }
                        is Resource.Loading -> {
                            // Already showing local results
                        }
                        is Resource.Error -> {
                            // Keep already showing local results
                        }
                    }
                }
            }
        } else {
            _uiState.update { it.copy(cryptoList = emptyList()) }
        }
    }

    fun saveSearchQuery(query: String) {
        if (query.isBlank()) return
        launch { repository.insertSearchHistory(query) }
    }

    private fun toggleFavorite(asset: AssetUiModel) {
        launch {
            toggleWatchlistUseCase(asset)
        }
    }
}
