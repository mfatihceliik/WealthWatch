package com.example.wealthwatch.core.util

import com.example.wealthwatch.domain.model.settings.AppCurrency
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WealthWatchFormatterImpl @Inject constructor() : WealthWatchFormatter {
    private val symbols = DecimalFormatSymbols(Locale.US)
    private val currencyFormat = ThreadLocal.withInitial { DecimalFormat("#,##0.00", symbols) }
    private val lowValueCurrencyFormat = ThreadLocal.withInitial { DecimalFormat("#,##0.000000", symbols) }
    private val cryptoAmountFormat = ThreadLocal.withInitial { DecimalFormat("#,##0.####", symbols) }
    private val percentageFormat = ThreadLocal.withInitial { DecimalFormat("#0.00", symbols) }

    override fun formatCurrency(amount: Double, currency: AppCurrency): String {
        return if (kotlin.math.abs(amount) in 0.0000001..0.9999999) {
            "${currency.symbol}${lowValueCurrencyFormat.get()?.format(amount)}"
        } else {
            "${currency.symbol}${currencyFormat.get()?.format(amount)}"
        }
    }

    override fun formatAmount(amount: Double): String {
        val formatted = cryptoAmountFormat.get()?.format(amount)
        return "$formatted"
    }

    override fun formatPercentage(percent: Double): String {
        val formatted = percentageFormat.get()?.format(percent)
        return if (percent > 0) "+$formatted%" else "$formatted%"
    }

    override fun formatPriceChange(amount: Double, currency: AppCurrency): String {
        val formatted = formatCurrency(kotlin.math.abs(amount), currency)
        return when {
            amount > 0 -> "+$formatted"
            amount < 0 -> "-$formatted"
            else -> formatted
        }
    }

    override fun formatProfitLossValue(amount: Double, currency: AppCurrency): String {
        val absoluteAmount = kotlin.math.abs(amount)
        return if (absoluteAmount in 0.0000001..0.9999999) {
            "${currency.symbol}${lowValueCurrencyFormat.get()?.format(absoluteAmount)}"
        } else {
            "${currency.symbol}${currencyFormat.get()?.format(absoluteAmount)}"
        }
    }

    override fun formatVolume(volume: Double): String {
        return when {
            volume >= 1_000_000_000 -> "${cryptoAmountFormat.get()?.format(volume / 1_000_000_000)}B"
            volume >= 1_000_000 -> "${cryptoAmountFormat.get()?.format(volume / 1_000_000)}M"
            else -> cryptoAmountFormat.get()?.format(volume) ?: ""
        }
    }

    override fun formatCompactVolume(volume: Double): String {
        return when {
            volume >= 1_000_000_000 -> "${percentageFormat.get()?.format(volume / 1_000_000_000)}B"
            volume >= 1_000_000 -> "${percentageFormat.get()?.format(volume / 1_000_000)}M"
            else -> percentageFormat.get()?.format(volume) ?: ""
        }
    }

    override fun formatDistribution(percent: Double): String {
        // Formats as "12%" or "12.5%" (optional 1 decimal place)
        return DecimalFormat("#0.#", symbols).format(percent) + "%"
    }
}
