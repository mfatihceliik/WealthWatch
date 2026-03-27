package com.example.wealthwatch.presentation.asset_detail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.wealthwatch.R
import com.example.wealthwatch.core.provider.ResourceProvider
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.domain.model.asset.Transaction
import com.example.wealthwatch.domain.repository.local.settings.SettingsRepository
import com.example.wealthwatch.domain.use_case.asset.BuyAssetUseCase
import com.example.wealthwatch.domain.use_case.asset.GetAssetDetailsUseCase
import com.example.wealthwatch.domain.use_case.asset.SellAssetUseCase
import com.example.wealthwatch.domain.use_case.market.GetExchangeRatesUseCase
import com.example.wealthwatch.domain.use_case.transaction.DeleteTransactionUseCase
import com.example.wealthwatch.presentation.base.BaseUiEvent
import com.example.wealthwatch.presentation.main.topbar.TopBarEvent
import com.example.wealthwatch.presentation.base.BaseViewModel
import com.example.wealthwatch.presentation.base.ScreenState
import com.example.wealthwatch.presentation.mapper.AssetUiMapper
import com.example.wealthwatch.presentation.model.AssetUiModel
import com.example.wealthwatch.presentation.navigation.routes.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AssetDetailViewModel
@Inject constructor(
    private val getAssetDetailsUseCase: GetAssetDetailsUseCase,
    private val buyAssetUseCase: BuyAssetUseCase,
    private val sellAssetUseCase: SellAssetUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val getExchangeRatesUseCase: GetExchangeRatesUseCase,
    private val settingsRepository: SettingsRepository,
    private val resourceProvider: ResourceProvider,
    private val assetUiMapper: AssetUiMapper,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<AssetDetailUiState> =
        MutableStateFlow(AssetDetailUiState())
    val uiState: StateFlow<AssetDetailUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<AssetDetailUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val route = savedStateHandle.toRoute<Route.AssetDetail>()
    val symbol = route.symbol

    init {
        setTopBarConfig(
            TopBarEvent.SetConfig(
                isTopbarVisible = true,
                titleString = symbol,
                showBackButton = true
            )
        )
        observeData()
    }

    private fun observeData() {
        launch {
            combine(
                getAssetDetailsUseCase(symbol).catch { emit(null) },
                settingsRepository.getCurrency(),
                getExchangeRatesUseCase()
            ) { assetWithTransactions, currency, rates ->

                val rate = 1.0

                if (assetWithTransactions != null) {
                    val asset = assetWithTransactions.asset
                    val mappedAsset = assetUiMapper.mapToCurrency(
                        asset, currency, rate
                    )

                    Triple(mappedAsset, assetWithTransactions.transactions, Pair(currency, rate))
                } else {
                    // Use type from route for new assets to ensure correct grouping
                    val defaultAsset = AssetUiModel(
                        symbol = symbol,
                        name = symbol, // Fallback name
                        type = AssetType.fromCode(route.typeCode)
                    )
                    Triple(defaultAsset, emptyList(), Pair(currency, rate))
                }
            }.collect { (mappedAsset, transactions, currencyRate) ->
                _uiState.update {
                    it.copy(
                        screenState = ScreenState.HAS_DATA,
                        asset = mappedAsset,
                        transactions = transactions,
                        currency = currencyRate.first,
                        exchangeRate = currencyRate.second
                    )
                }
            }
        }
    }

    fun onEvent(event: AssetDetailUiEvent) {
        when (event) {
            is AssetDetailUiEvent.OnAmountChange -> {
                _uiState.update { it.copy(amountInput = event.amount) }
            }

            is AssetDetailUiEvent.OnPriceChange -> {
                _uiState.update { it.copy(priceInput = event.price) }
            }

            is AssetDetailUiEvent.OnNoteChange -> {
                _uiState.update { it.copy(noteInput = event.note) }
            }

            is AssetDetailUiEvent.OnToggleMode -> {
                _uiState.update { it.copy(isBuyMode = event.isBuy) }
            }

            is AssetDetailUiEvent.OnBuyClick -> {
                val amount = _uiState.value.amountInput.toDoubleOrNull() ?: 0.0
                val price = _uiState.value.priceInput.toDoubleOrNull() ?: 0.0
                val note = _uiState.value.noteInput
                if (amount > 0 && price > 0) {
                    buyAsset(amount, price, note)
                }
            }

            is AssetDetailUiEvent.OnSellClick -> {
                val amount = _uiState.value.amountInput.toDoubleOrNull() ?: 0.0
                val price = _uiState.value.priceInput.toDoubleOrNull() ?: 0.0
                val note = _uiState.value.noteInput
                if (amount > 0 && price > 0) {
                    sellAsset(amount, price, note)
                }
            }

            is AssetDetailUiEvent.OnDeleteTransaction -> {
                deleteTransaction(event.transaction)
            }

            else -> {}
        }
    }

    private fun buyAsset(amount: Double, price: Double, note: String) {
        launch {
            try {
                val name = _uiState.value.asset?.name ?: return@launch
                val type = _uiState.value.asset?.type ?: return@launch

                buyAssetUseCase(
                    symbol = symbol,
                    name = name,
                    type = type,
                    amount = amount,
                    price = price,
                    note = note
                )
                sendBaseEvent(BaseUiEvent.ShowSnackBar("Asset Purchased"))
                _eventFlow.emit(AssetDetailUiEvent.TransactionSuccess)
                // Clear Inputs
                _uiState.update { it.copy(amountInput = "", priceInput = "", noteInput = "") }
            } catch (e: Exception) {
                sendBaseEvent(BaseUiEvent.ShowSnackBar(e.message ?: "Error"))
            }
        }
    }

    private fun sellAsset(amount: Double, price: Double, note: String) {
        val currentAmount = _uiState.value.asset?.amount ?: 0.0
        if (amount > currentAmount) {
            sendBaseEvent(BaseUiEvent.ShowSnackBar("Insufficient balance"))
            return
        }

        launch {
            try {
                val name = _uiState.value.asset?.name ?: return@launch
                val type = _uiState.value.asset?.type ?: return@launch

                sellAssetUseCase(
                    symbol = symbol,
                    name = name,
                    type = type,
                    amount = amount,
                    price = price,
                    note = note
                )
                sendBaseEvent(BaseUiEvent.ShowSnackBar("Asset Sold"))
                _eventFlow.emit(AssetDetailUiEvent.TransactionSuccess)
                // Clear Inputs
                _uiState.update { it.copy(amountInput = "", priceInput = "", noteInput = "") }
            } catch (e: Exception) {
                sendBaseEvent(
                    BaseUiEvent.ShowSnackBar(
                        e.message ?: "An error occurred while adding the asset."
                    )
                )
            }
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        launch {
            try {
                deleteTransactionUseCase(transaction)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        screenState = ScreenState.ERROR,
                        message = e.message ?: resourceProvider.getString(
                            R.string.error_process_delete
                        )
                    )
                }
            }
        }
    }
}
