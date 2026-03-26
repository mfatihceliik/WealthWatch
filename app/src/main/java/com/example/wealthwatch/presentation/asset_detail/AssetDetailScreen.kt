package com.example.wealthwatch.presentation.asset_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wealthwatch.R
import com.example.wealthwatch.presentation.common.BaseScreen
import com.example.wealthwatch.presentation.components.SwipeToDeleteContainer
import com.example.wealthwatch.presentation.components.TransactionItem
import com.example.wealthwatch.presentation.components.WWButton
import com.example.wealthwatch.presentation.components.WWCard
import com.example.wealthwatch.presentation.components.WWText
import com.example.wealthwatch.presentation.components.WWTextField
import com.example.wealthwatch.presentation.main.topbar.TopBarEvent
import com.example.wealthwatch.presentation.model.AssetUiModel
import com.example.wealthwatch.ui.theme.AppTheme
@Composable
fun AssetDetailScreen(
    viewModel: AssetDetailViewModel = hiltViewModel(),
    symbol: String,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val spacing = AppTheme.spacing
    BaseScreen(
        state = uiState,
        viewModel = viewModel
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {

            // Chart Placeholder Section
            item(key = "chart") {
                ChartPlaceholderSection()
            }

            // Summary Section
            item(key = "summary") {
                AssetSummarySection(
                    asset = uiState.asset,
                    symbol = symbol
                )
            }

            // Transaction Input Section
            item(key = "transaction") {
                TransactionInputSection(
                    amountText = uiState.amountInput,
                    onAmountChange = { viewModel.onEvent(AssetDetailUiEvent.OnAmountChange(it)) },
                    priceText = uiState.priceInput,
                    onPriceChange = { viewModel.onEvent(AssetDetailUiEvent.OnPriceChange(it)) },
                    noteText = uiState.noteInput,
                    onNoteChange = { viewModel.onEvent(AssetDetailUiEvent.OnNoteChange(it)) },
                    isBuy = uiState.isBuyMode,
                    onToggleMode = { isBuy -> viewModel.onEvent(AssetDetailUiEvent.OnToggleMode(isBuy)) },
                    onBuy = { viewModel.onEvent(AssetDetailUiEvent.OnBuyClick) },
                    onSell = { viewModel.onEvent(AssetDetailUiEvent.OnSellClick) }
                )
            }

            // Transaction History Header
            item(key = "transaction_history") {
                WWText(
                    stringResource(R.string.transaction_history),
                    style = AppTheme.typography.titleMedium,
                    color = AppTheme.colors.onSurface
                )
            }

            // Transaction History List
            if (uiState.transactions.isEmpty()) {
                item(key = "no_transaction") {
                    WWText(
                        text = "No transactions yet.", // Consider adding to strings.xml later: stringResource(R.string.no_transactions)
                        style = AppTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = spacing.spaceMedium)
                    )
                }
            } else {
                items(uiState.transactions, key = { it.id }) { transaction ->
                    SwipeToDeleteContainer(
                        onDelete = { viewModel.onEvent(AssetDetailUiEvent.OnDeleteTransaction(transaction)) }) {
                        TransactionItem(
                            transaction = transaction
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AssetSummarySection(
    asset: AssetUiModel?,
    symbol: String
) {
    val spacing = AppTheme.spacing
    
    WWCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = AppTheme.colors.surfaceVariant
    ) {
        WWText(
            text = asset?.name ?: symbol,
            style = AppTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = AppTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                WWText(
                    stringResource(R.string.total_amount),
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colors.onSurfaceVariant
                )
                WWText(
                    text = asset?.formattedAmount ?: "",
                    style = AppTheme.typography.titleMedium,
                    color = AppTheme.colors.onSurface
                )
            }
            Column {
                WWText(
                    stringResource(R.string.average_cost),
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colors.onSurfaceVariant
                )
                WWText(
                    text = asset?.formattedAverageCost ?: "",
                    style = AppTheme.typography.titleMedium,
                    color = AppTheme.colors.onSurface
                )
            }
        }
    }
}

@Composable
fun ChartPlaceholderSection() {
    val spacing = AppTheme.spacing
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                AppTheme.colors.surfaceVariant.copy(alpha = 0.3f),
                RoundedCornerShape(spacing.spaceSmall)
            ),
        contentAlignment = Alignment.Center
    ) {
        WWText(
            text = "Chart Area (Coming Soon)\n1D 7D 1M 3M 1Y 5Y",
            style = AppTheme.typography.bodyMedium,
            color = AppTheme.colors.onSurfaceVariant
        )
    }
}

@Composable
fun TransactionInputSection(
    amountText: String,
    onAmountChange: (String) -> Unit,
    priceText: String,
    onPriceChange: (String) -> Unit,
    noteText: String,
    onNoteChange: (String) -> Unit,
    isBuy: Boolean,
    onToggleMode: (Boolean) -> Unit,
    onBuy: () -> Unit,
    onSell: () -> Unit
) {
    val spacing = AppTheme.spacing
    
    WWCard(
        modifier = Modifier.fillMaxWidth(),
    ) {
        // Buy/Sell Toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppTheme.colors.surfaceVariant, RoundedCornerShape(spacing.spaceSmall))
                .padding(
                    horizontal = spacing.spaceSmall,
                    vertical = spacing.spaceMedium
                )
        ) {
            // Buy Tab
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(spacing.spaceSmall))
                    .background(if (isBuy) AppTheme.colors.background else AppTheme.colors.surfaceVariant)
                    .clickable { onToggleMode(true) }
                    .padding(vertical = spacing.spaceSmall),
                contentAlignment = Alignment.Center
            ) {
                WWText(
                    text = stringResource(R.string.buy_button), // Using existing "Buy" string
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colors.priceUp
                )
            }

            // Sell Tab
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(spacing.spaceSmall))
                    .background(if (!isBuy) AppTheme.colors.background else Color.Transparent)
                    .clickable { onToggleMode(false) }
                    .padding(vertical = spacing.spaceSmall),
                contentAlignment = Alignment.Center
            ) {
                WWText(
                    text = stringResource(R.string.transaction_sell), // Using existing "Sell" string
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colors.priceDown
                )
            }
        }

        // Inputs
        WWTextField(
            value = amountText,
            onValueChange = { onAmountChange(it.replace(',', '.')) },
            label = stringResource(R.string.amount_label),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true
        )

        WWTextField(
            value = priceText,
            onValueChange = { onPriceChange(it.replace(',', '.')) },
            label = stringResource(R.string.price_label),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true
        )

        WWTextField(
            value = noteText,
            onValueChange = { onNoteChange(it) },
            label = stringResource(R.string.note_label),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(spacing.spaceMedium))

        // Action Button
        WWButton(
            text = if (isBuy) stringResource(R.string.buy_button) else stringResource(R.string.transaction_sell).uppercase(),
            onClick = {
                if (isBuy) onBuy() else onSell()
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
