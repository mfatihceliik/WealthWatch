package com.example.wealthwatch.presentation.search_asset

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.domain.model.asset.MarketAsset
import com.example.wealthwatch.domain.model.settings.AppCurrency
import com.example.wealthwatch.domain.repository.local.search_history.SearchHistoryRepository
import com.example.wealthwatch.domain.repository.local.settings.SettingsRepository
import com.example.wealthwatch.domain.use_case.search.GetSearchInitialDataUseCase
import com.example.wealthwatch.domain.use_case.search.SearchAssetsUseCase
import com.example.wealthwatch.domain.model.search.SearchInitialData
import com.example.wealthwatch.presentation.base.BaseViewModel
import com.example.wealthwatch.presentation.base.ScreenState
import com.example.wealthwatch.presentation.main.topbar.TopBarEvent
import com.example.wealthwatch.presentation.mapper.AssetUiMapper
import com.example.wealthwatch.presentation.model.AssetUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SearchAssetViewModel @Inject constructor(
    private val getSearchInitialDataUseCase: GetSearchInitialDataUseCase,
    private val searchAssetsUseCase: SearchAssetsUseCase,
    private val searchHistoryRepository: SearchHistoryRepository,
    private val assetUiMapper: AssetUiMapper,
    private val settingsRepository: SettingsRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(SearchAssetUiState())
    val uiState: StateFlow<SearchAssetUiState> = _uiState.asStateFlow()
    private var searchJob: Job? = null
    private var fullInitialData: SearchInitialData? = null
    private var currentCurrency: AppCurrency = AppCurrency.USD

    init {
        // Initial state is loading, so TopBar should be hidden (or search hidden)
        setTopBarConfig(TopBarEvent.SetConfig(isTopbarVisible = true, isSearchVisible = false))

        launch {
            settingsRepository.getCurrency().collect { currentCurrency = it }
        }

        launch {
            searchHistoryRepository.getSearchHistory().collect { history ->
                _uiState.update { it.copy(searchHistory = history.map { entity -> entity.query }) }
            }
        }

        loadInitialData()
    }

    private fun loadInitialData() {
        launch {
            getSearchInitialDataUseCase().collect { result ->
                when(result) {
                    is Resource.Success -> {
                        fullInitialData = result.data
                        filterInitialData()
                        // Data loaded, show search bar
                        setTopBarConfig(
                            TopBarEvent.SetConfig(
                                isTopbarVisible = true,
                                isSearchVisible = true,
                                showBackButton = false
                            )
                        )
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                screenState = ScreenState.HAS_DATA,
                                message = result.message
                            )
                        }
                        setTopBarConfig(TopBarEvent.SetConfig(isTopbarVisible = true, isSearchVisible = true))
                    }
                    is Resource.Loading -> {
                        _uiState.update { it.copy(screenState = ScreenState.LOADING) }
                        setTopBarConfig(TopBarEvent.SetConfig(isTopbarVisible = true, isSearchVisible = false))
                    }
                }
            }
        }
    }

    fun onEvent(event: SearchAssetEvent) {
        when(event) {
            is SearchAssetEvent.OnSearchQueryChange -> {
                if (_uiState.value.searchQuery != event.query) {
                    _uiState.update { it.copy(searchQuery = event.query) }
                    performSearch(event.query)
                }
            }
            is SearchAssetEvent.OnFilterSelect -> {
                _uiState.update { it.copy(selectedFilter = event.filter) }
                if (_uiState.value.searchQuery.isNotEmpty()) {
                    performSearch(_uiState.value.searchQuery)
                } else {
                    filterInitialData()
                }
            }
            is SearchAssetEvent.OnAssetClick -> {
                 if (_uiState.value.searchQuery.isNotBlank()) {
                    launch(Dispatchers.IO) {
                        searchHistoryRepository.insertSearchHistory(_uiState.value.searchQuery)
                    }
                }
            }
            is SearchAssetEvent.OnDeleteHistoryItem -> {
                launch(Dispatchers.IO) {
                    searchHistoryRepository.deleteSearchHistory(event.query)
                }
            }
            SearchAssetEvent.OnClearHistory -> {
                launch(Dispatchers.IO) {
                    searchHistoryRepository.clearSearchHistory()
                }
            }
        }
    }

    private fun performSearch(query: String) {
        searchJob?.cancel()
        searchJob = launch(Dispatchers.Default) {
            if (query.isBlank()) {
                _uiState.update { it.copy(searchResults = emptyList()) }
                return@launch
            }
            
            delay(500L) // Debounce
            
            searchAssetsUseCase(query).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        val assets = result.data ?: emptyList()
                        val uiModels = mutableListOf<AssetUiModel>()
                        assets.forEach { asset ->
                            uiModels.add(assetUiMapper.mapToNative(asset))
                        }
                        _uiState.update { it.copy(searchResults = uiModels) }
                    }
                    is Resource.Error -> {
                         // Optionally handle error
                    }
                    is Resource.Loading -> {
                        // Optionally set loading state for search results
                    }
                }
            }
        }
    }

    private fun filterInitialData() {
        val data = fullInitialData ?: return
        val filter = _uiState.value.selectedFilter
        
        launch(Dispatchers.Default) {
            val filteredTop = filterAssets(data.topMovers, filter)
            val filteredSuggested = filterAssets(data.suggestedAssets, filter)
            
            val topUi = mutableListOf<AssetUiModel>()
            filteredTop.forEach { topUi.add(assetUiMapper.mapToNative(it)) }

            val suggestedUi = mutableListOf<AssetUiModel>()
            filteredSuggested.forEach { suggestedUi.add(assetUiMapper.mapToNative(it)) }

            _uiState.update {
                it.copy(
                    screenState = ScreenState.HAS_DATA,
                    topMovers = topUi,
                    suggestedAssets = suggestedUi
                )
            }
        }
    }

    private fun filterAssets(assets: List<MarketAsset>, filter: AssetFilter): List<MarketAsset> {
        val filterCode = filter.type?.code ?: return assets
        return assets.filter { asset ->
            asset.type.code.equals(filterCode, ignoreCase = true)
        }
    }
}
