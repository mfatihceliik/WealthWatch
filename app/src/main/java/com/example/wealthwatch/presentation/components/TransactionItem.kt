package com.example.wealthwatch.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.wealthwatch.R
import com.example.wealthwatch.domain.model.asset.Transaction
import com.example.wealthwatch.ui.theme.AppTheme
import com.example.wealthwatch.ui.theme.LocalSpacing
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TransactionItem(
    modifier: Modifier = Modifier,
    transaction: Transaction,
) {
    val spacing = LocalSpacing.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.spaceMedium),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            WWText(
                text = if (transaction.isBuy) stringResource(R.string.transaction_buy)
                else stringResource(R.string.transaction_sell),
                color = if (transaction.isBuy) AppTheme.colors.priceUp else AppTheme.colors.priceDown,
                fontWeight = FontWeight.Bold
            )
            WWText(
                text = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault()).format(
                    Date(
                        transaction.date
                    )
                ), style = AppTheme.typography.bodySmall,
            )
        }
        Column(
            horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.SpaceEvenly
        ) {
            WWText(
                text = "${transaction.amount} ${stringResource(R.string.amount_label)}",
                fontWeight = FontWeight.SemiBold
            )
            WWText(
                text = "${transaction.price}",
                style = AppTheme.typography.bodySmall
            )
        }
    }
}

@Preview
@Composable
private fun TransactionItemPreview() {
    com.example.wealthwatch.ui.theme.WealthWatchTheme {
        TransactionItem(
            transaction = Transaction(
                id = 1,
                assetSymbol = "BTC",
                amount = 0.5,
                price = 45000.0,
                totalValue = 22500.0,
                date = System.currentTimeMillis(),
                isBuy = true,
                note = "Bought dip"
            )
        )
    }
}
