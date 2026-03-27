package com.example.wealthwatch.presentation.watchlist

import com.example.wealthwatch.R
import com.example.wealthwatch.presentation.main.topbar.TopBarEvent
import com.example.wealthwatch.domain.repository.local.settings.SettingsRepository
import com.example.wealthwatch.domain.use_case.watchlist.GetWatchlistStreamUseCase
import com.example.wealthwatch.domain.use_case.watchlist.ToggleWatchlistUseCase
import com.example.wealthwatch.presentation.base.BaseViewModel
import com.example.wealthwatch.presentation.base.ScreenState
import com.example.wealthwatch.presentation.mapper.AssetUiMapper
import com.example.wealthwatch.presentation.model.AssetUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val getWatchlistStreamUseCase: GetWatchlistStreamUseCase,
    private val toggleWatchlistUseCase: ToggleWatchlistUseCase,
    private val settingsRepository: SettingsRepository,
    private val assetUiMapper: AssetUiMapper
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(WatchlistState())
    val uiState: StateFlow<WatchlistState> = _uiState.asStateFlow()

    init {
        setTopBarConfig(TopBarEvent.SetConfig(isTopbarVisible = true, title = R.string.watchlist))
        getWatchlist()
    }

    private fun getWatchlist() {
        launch {
            combine(
                getWatchlistStreamUseCase(), settingsRepository.getCurrency()
            ) { tickers, currency ->
                Pair(tickers, currency)
            }.collect { (tickers, currency) ->
                if (tickers.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            screenState = ScreenState.NO_DATA,
                            cryptoList = emptyList(),
                            currency = currency
                        )
                    }
                } else {
                    val cryptoList = tickers.map {
                         assetUiMapper.mapToNative(it, isFavorite = true)
                    }

                    _uiState.update {
                        it.copy(
                            screenState = ScreenState.HAS_DATA,
                            cryptoList = cryptoList,
                            currency = currency
                        )
                    }
                }
            }
        }
    }

    fun toggleFavorite(asset: AssetUiModel) {
        // Watchlist items are already in favorites, so we just toggle (remove).
        launch {
            toggleWatchlistUseCase(asset)
        }
    }
}
