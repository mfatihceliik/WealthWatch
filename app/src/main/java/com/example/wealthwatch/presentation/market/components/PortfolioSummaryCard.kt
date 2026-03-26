package com.example.wealthwatch.presentation.market.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.wealthwatch.R
import com.example.wealthwatch.domain.model.settings.AppCurrency
import com.example.wealthwatch.presentation.components.WWCard
import com.example.wealthwatch.presentation.components.WWDropdownMenu
import com.example.wealthwatch.presentation.components.WWText
import com.example.wealthwatch.ui.theme.AppTheme

@Composable
fun PortfolioSummaryCard(
    modifier: Modifier = Modifier,
    formattedTotalBalance: String,
    currency: AppCurrency? = null,
    onCurrencyChange: ((AppCurrency) -> Unit)? = null
) {
    var isBalanceVisible by rememberSaveable { mutableStateOf(true) }
    val spacing = AppTheme.spacing

    WWCard(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(spacing.spaceSmall)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                WWText(
                    text = stringResource(R.string.total_balance),
                    style = AppTheme.typography.labelLarge,
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(spacing.spaceSmall)
                ) {
                    if (currency != null && onCurrencyChange != null) {
                        WWDropdownMenu(
                            items = AppCurrency.entries,
                            selectedItem = currency,
                            onItemSelect = onCurrencyChange,
                            itemLabel = { it.code }
                        )
                    }

                    Icon(
                        imageVector = if (isBalanceVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Toggle Balance Visibility",
                        tint = AppTheme.colors.icon,
                        modifier = Modifier
                            .size(spacing.defaultIcon)
                            .clickable { isBalanceVisible = !isBalanceVisible }
                    )
                }
            }
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            WWText(
                text = if (isBalanceVisible) {
                    formattedTotalBalance
                } else {
                    "****"
                },
                style = AppTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = AppTheme.colors.primary
            )
        }
    }
}
