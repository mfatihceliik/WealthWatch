package com.example.wealthwatch.domain.model.settings

import androidx.annotation.StringRes
import com.example.wealthwatch.R

enum class AppCurrency(
    val code: String,
    val symbol: String,
    @StringRes val titleResId: Int
) {
    USD("USD", "$", R.string.currency_usd),
    EUR("EUR", "€", R.string.currency_eur),
    TRY("TRY", "₺", R.string.currency_try),
    GBP("GBP", "£", R.string.currency_gbp);
}
